package com.fetchrewards.receiptprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
import com.fetchrewards.receiptprocessor.service.ReceiptService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ReceiptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ReceiptService receiptService;

    @Test
    void shouldReturnReceiptId_WhenProcessingValidReceipt() throws Exception {
        // Arrange
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
                List.of(new Item("Pepsi 2L", "1.99")), "1.99");

        // Act & Assert
        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldReturnPoints_WhenReceiptIdIsValid() throws Exception {
        // Arrange
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
        String id = receiptService.processReceipt(receipt);

        // Act & Assert
        mockMvc.perform(get("/receipts/" + id + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists());
    }

    @Test
    void shouldReturnNotFound_WhenReceiptIdIsInvalid() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/receipts/invalid-id/points"))
                .andExpect(status().isNotFound());
    }
}