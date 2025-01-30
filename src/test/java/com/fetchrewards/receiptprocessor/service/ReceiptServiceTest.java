package com.fetchrewards.receiptprocessor.service;

import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

class ReceiptServiceTest {

    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptService();
    }

    @Test
    void testCalculatePoints() {
        List<Item> items = Arrays.asList(
                new Item("Mountain Dew 12PK", "6.49"),
                new Item("Doritos Nacho Cheese", "3.35")
        );

        Receipt receipt = new Receipt("Target", "2022-01-01", "13:01", items, "9.84");

        int points = receiptService.calculatePoints(receipt);
        assertTrue(points > 0 );
    }

    @Test
    void testProcessReceipt() {
        List<Item> items = List.of(new Item("Milk", "2.99"));
        Receipt receipt = new Receipt("Walmart", "2022-05-10", "15:30", items, "2.99");

        String id = receiptService.processReceipt(receipt);
        assertNotNull(id);
    }
}
