package com.fetchrewards.receiptprocessor.controller;

import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * Controller for handling receipt-related requests.
 */
@RestController
@RequestMapping("/receipts")
public class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    /**
     * Processes the given receipt and returns a unique ID.
     */
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }

    /**
     * Retrieves the reward points for the receipt with the given ID.
     */
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        int points = receiptService.getPoints(id);
        return ResponseEntity.ok(Collections.singletonMap("points", points));
    }
}