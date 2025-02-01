package com.fetchrewards.receiptprocessor.controller;

import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.OK)
    public Map<String, String> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return Collections.singletonMap("id", id);
    }

    /**
     * Retrieves the reward points for the receipt with the given ID.
     */
    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        return Collections.singletonMap("points", receiptService.getPoints(id));
    }
}