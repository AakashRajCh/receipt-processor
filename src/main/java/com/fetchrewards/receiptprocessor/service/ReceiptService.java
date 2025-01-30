package com.fetchrewards.receiptprocessor.service;

import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

// Service for Receipt Processing
@Service
public class ReceiptService {
    private final Map<String, Receipt> receiptStore = new ConcurrentHashMap<>();

    public String processReceipt(Receipt receipt) {
        String id = UUID.randomUUID().toString();
        receiptStore.put(id, receipt);
        return id;
    }

    public int calculatePoints(Receipt receipt) {
        int points = receipt.getRetailer().replaceAll("[^a-zA-Z0-9]", "").length();

        double total = Double.parseDouble(receipt.getTotal());
        if (total == Math.floor(total)) {
            points += 50;
        }
        if (total % 0.25 == 0) {
            points += 25;
        }
        points += (receipt.getItems().size() / 2) * 5;

        for (Item item : receipt.getItems()) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                points += (int) Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
            }
        }
        int day = Integer.parseInt(receipt.getPurchaseDate().split("-")[2]);
        if (day % 2 != 0) {
            points += 6;
        }
        int hour = Integer.parseInt(receipt.getPurchaseTime().split(":" )[0]);
        if (hour >= 14 && hour < 16) {
            points += 10;
        }
        return points;
    }

    public Integer getPoints(String id) {
        Receipt receipt = receiptStore.get(id);
        return (receipt != null) ? calculatePoints(receipt) : null;
    }
}