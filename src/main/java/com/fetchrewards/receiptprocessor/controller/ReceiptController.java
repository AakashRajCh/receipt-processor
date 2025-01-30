package com.fetchrewards.receiptprocessor.controller;

import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;
import java.util.NoSuchElementException;

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
     *
     * @param receipt the receipt to process
     * @return a map containing the unique ID of the processed receipt
     */
    @PostMapping("/process")
    public Map<String, String> processReceipt(@RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return Collections.singletonMap("id", id);
    }
    /**
     * Retrieves the reward points for the receipt with the given ID.
     *
     * @param id the ID of the receipt
     * @return a map containing the reward points
     */
    @GetMapping("/{id}/points")
    public Map<String, Integer> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id)
                .orElseThrow(() -> new NoSuchElementException("No receipt found for that ID."));
        return Collections.singletonMap("points", points);
    }

    /**
     * Handles NoSuchElementException and returns a 404 Not Found status.
     *
     * @param ex the exception
     * @return the error message
     */
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoSuchElementException(NoSuchElementException ex) {
        return Collections.singletonMap("error", ex.getMessage());
    }
}