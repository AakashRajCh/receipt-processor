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
     * @throws NoSuchReceiptException if no receipt is found for the given ID
     */
    public int getPoints(String id) {
        Receipt receipt = receiptStore.get(id);
        if (receipt == null) {
            throw new NoSuchReceiptException("No receipt found for that ID.");
        }
        return calculatePoints(receipt);
    }

    /**
     * Calculates the total reward points for the given receipt.
     *
     * @param receipt the receipt to calculate points for
     * @return the total reward points
     */
    private int calculatePoints(Receipt receipt) {
        int points = 0;

        points += calculateRetailerPoints(receipt.getRetailer());
        points += calculateTotalPoints(receipt.getTotal());
        points += calculateItemPoints(receipt.getItems());
        points += calculateDatePoints(receipt.getPurchaseDate());
        points += calculateTimePoints(receipt.getPurchaseTime());

        return points;
    }

    /**
     * Calculates points based on the retailer's name.
     *
     * @param retailer the retailer's name
     * @return the points based on the length of the retailer's name
     */
    private int calculateRetailerPoints(String retailer) {
        return retailer.replaceAll("[^a-zA-Z0-9]", "").length();
    }

    /**
     * Calculates points based on the total amount of the receipt.
     *
     * @param totalStr the total amount as a string
     * @return the points based on the total amount
     */
    private int calculateTotalPoints(String totalStr) {
        double total = Double.parseDouble(totalStr);
        int points = 0;

        if (total == Math.floor(total)) {
            points += WHOLE_NUMBER_POINTS;
        }

        if (total % 0.25 == 0) {
            points += QUARTER_INCREMENT_POINTS;
        }

        return points;
    }

    /**
     * Calculates points based on the items in the receipt.
     *
     * @param items the list of items
     * @return the points based on the items
     */
    private int calculateItemPoints(java.util.List<Item> items) {
        int points = (items.size() / 2) * ITEM_PAIR_POINTS;

        for (Item item : items) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }

        return points;
    }

    /**
     * Calculates points based on the purchase date.
     *
     * @param purchaseDate the purchase date in format YYYY-MM-DD
     * @return the points based on the purchase date
     */
    private int calculateDatePoints(String purchaseDate) {
        int day = Integer.parseInt(purchaseDate.split("-")[2]);
        return (day % 2 != 0) ? ODD_DAY_POINTS : 0;
    }

    /**
     * Calculates points based on the purchase time.
     *
     * @param purchaseTime the purchase time in format HH:MM
     * @return the points based on the purchase time
     */
    private int calculateTimePoints(String purchaseTime) {
        int hour = Integer.parseInt(purchaseTime.split(":")[0]);
        return (hour >= AFTERNOON_START_HOUR && hour < AFTERNOON_END_HOUR) ? AFTERNOON_HOURS_POINTS : 0;
    }
}