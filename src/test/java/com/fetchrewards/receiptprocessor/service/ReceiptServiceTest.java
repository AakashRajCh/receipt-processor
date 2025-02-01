//package com.fetchrewards.receiptprocessor.service;
//
//import com.fetchrewards.receiptprocessor.model.Item;
//import com.fetchrewards.receiptprocessor.model.Receipt;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class ReceiptServiceTest {
//
//    private final ReceiptService receiptService = new ReceiptService();
//
//    @Test
//    void shouldProcessReceiptAndReturnId() {
//
//        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
//                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
//
//        String id = receiptService.processReceipt(receipt);
//
//        assertTrue(receiptService.getPoints(id).isPresent());
//    }
//
//    @Test
//    void shouldCalculatePointsCorrectly() {
//
//        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
//                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
//
//        int points = receiptService.calculatePoints(receipt);
//
//        assertEquals(23, points);
//    }
//
//    @Test
//    void shouldReturnPointsForValidReceiptId() {
//
//        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
//                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
//        String id = receiptService.processReceipt(receipt);
//
//        Optional<Integer> points = receiptService.getPoints(id);
//
//        assertTrue(points.isPresent());
//        assertEquals(23, points.get());
//    }
//
//    @Test
//    void shouldReturnEmptyOptionalForInvalidReceiptId() {
//        Optional<Integer> points = receiptService.getPoints("invalid-id");
//
//        assertTrue(points.isEmpty());
//    }
//}