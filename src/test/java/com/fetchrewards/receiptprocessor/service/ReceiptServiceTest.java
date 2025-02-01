package com.fetchrewards.receiptprocessor.service;

import com.fetchrewards.receiptprocessor.exception.NoSuchReceiptException;
import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReceiptServiceTest {

    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        receiptService = new ReceiptService();
    }

    @Test
    void shouldProcessReceiptAndReturnId() {
        Receipt receipt = new Receipt("Target", "2023-03-10", "15:30",
                List.of(new Item("Coke 1L", "2.50")), "2.50");

        String receiptId = receiptService.processReceipt(receipt);

        assertNotNull(receiptId);
        assertFalse(receiptId.isEmpty());
    }

    @Test
    void shouldCalculatePointsCorrectly() {
        Receipt receipt = new Receipt("Target", "2023-03-11", "15:00",
                List.of(new Item("Coke 1L", "2.50"), new Item("Pepsi 2L", "3.00")), "5.50");

        String receiptId = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(receiptId);

        assertTrue(points > 0);
    }

    @Test
    void shouldThrowException_WhenReceiptIdIsInvalid() {
        assertThrows(NoSuchReceiptException.class, () -> receiptService.getPoints("invalid-id"));
    }

    @Test
    void shouldAwardPointsForOddDayPurchase() {
        Receipt receipt = new Receipt("Best Buy", "2023-03-15", "13:00",
                List.of(new Item("USB Cable", "5.00")), "5.00");

        String receiptId = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(receiptId);

        assertTrue(points >= 6);
    }

    @Test
    void shouldAwardPointsForAfternoonPurchase() {
        Receipt receipt = new Receipt("Walmart", "2023-03-10", "15:00",
                List.of(new Item("Notebook", "2.00")), "2.00");

        String receiptId = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(receiptId);

        assertTrue(points >= 10);
    }
}