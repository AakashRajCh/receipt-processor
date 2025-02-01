package com.fetchrewards.receiptprocessor.service;

import com.fetchrewards.receiptprocessor.exception.NoSuchReceiptException;
import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service for processing receipts and calculating reward points.
 */
@Service
public class ReceiptService {
    private static final int WHOLE_NUMBER_POINTS = 50;
    private static final int QUARTER_INCREMENT_POINTS = 25;
    private static final int ITEM_PAIR_POINTS = 5;
    private static final int ODD_DAY_POINTS = 6;
    private static final int AFTERNOON_HOURS_POINTS = 10;
    private static final int AFTERNOON_START_HOUR = 14;
    private static final int AFTERNOON_END_HOUR = 16;

    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();

    /**
     * Processes the given receipt and returns a unique ID.
     *
     * @param receipt the receipt to process
     * @return the unique ID of the processed receipt
     */
    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, receipt);
        return id;
    }

    /**
     * Retrieves the reward points for the receipt with the given ID.
     *
     * @param id the ID of the receipt
     * @return the calculated reward points
     */
    public int getPoints(String id) {
        Receipt receipt = receiptStore.get(id);
        if (receipt == null) {
            throw new NoSuchReceiptException("No receipt found for that ID.");
        }
        return calculatePoints(receipt);
    }

    /**
     * Calculates the reward points for a given receipt.
     *
     * @param receipt the receipt to calculate points for
     * @return the calculated points
     */
    private int calculatePoints(Receipt receipt) {
        int points = 0;

        // One point for every alphanumeric character in the retailer name.
        points += receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        // 50 points if the total is a round dollar amount with no cents.
        double total = Double.parseDouble(receipt.getTotal());
        if (total == Math.floor(total)) {
            points += WHOLE_NUMBER_POINTS;
        }

        // 25 points if the total is a multiple of 0.25.
        if (total % 0.25 == 0) {
            points += QUARTER_INCREMENT_POINTS;
        }

        // 5 points for every two items on the receipt.
        points += (receipt.getItems().size() / 2) * ITEM_PAIR_POINTS;

        // If the trimmed length of the item description is a multiple of 3,
        // multiply the price by 0.2 and round up to the nearest integer.
        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        // 6 points if the day in the purchase date is odd.
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) {
            points += ODD_DAY_POINTS;
        }

        // 10 points if the time of purchase is after 2:00pm and before 4:00pm.
        int hour = Integer.parseInt(receipt.getPurchaseTime().split(":")[0]);
        if (hour >= AFTERNOON_START_HOUR && hour < AFTERNOON_END_HOUR) {
            points += AFTERNOON_HOURS_POINTS;
        }

        return points;
    }
}