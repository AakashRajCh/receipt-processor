package com.fetchrewards.receiptprocessor.controller;

import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "Process a receipt", description = "Processes the given receipt and returns a unique ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receipt processed successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid receipt data")
    })
    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@Valid @RequestBody Receipt receipt) {
        String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Collections.singletonMap("id", id));
    }

    /**
     * Retrieves the reward points for the receipt with the given ID.
     */
    @Operation(summary = "Get receipt points", description = "Retrieves the reward points for the receipt with the given ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Points retrieved successfully"),
        @ApiResponse(responseCode = "404", description = "Receipt not found")
    })
    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable String id) {
        int points = receiptService.getPoints(id);
        return ResponseEntity.ok(Collections.singletonMap("points", points));
    }
}