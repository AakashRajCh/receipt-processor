package com.fetchrewards.receiptprocessor.controller;
import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;


// Controller for handling requests
@RestController
@RequestMapping("/receipts")
class ReceiptController {
    private final ReceiptService receiptService;

    public ReceiptController(ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return Collections.singletonMap("id", id);
    }

    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == null) {
            throw new NoSuchElementException("No receipt found for that ID.");
        }
        return Collections.singletonMap("points", points);
    }
}