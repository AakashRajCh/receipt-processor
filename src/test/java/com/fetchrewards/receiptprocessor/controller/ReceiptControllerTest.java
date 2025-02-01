package com.fetchrewards.receiptprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetchrewards.receiptprocessor.model.Item;
import com.fetchrewards.receiptprocessor.model.Receipt;
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

    @Test
    void shouldReturnReceiptId_WhenValidReceiptIsProcessed() throws Exception {
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
                List.of(new Item("Pepsi 2L", "1.99")), "1.99");

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void shouldReturnBadRequest_WhenReceiptIsInvalid() throws Exception {
        Receipt receipt = new Receipt("Walmart", null, "14:15",
                List.of(new Item("Pepsi 2L", "1.99")), "1.99");

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The receipt is invalid."));
    }

    @Test
    void shouldReturnPoints_WhenReceiptIdIsValid() throws Exception {
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
                List.of(new Item("Pepsi 2L", "1.99")), "1.99");

        String response = mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String id = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/receipts/" + id + "/points"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.points").exists());
    }

    @Test
    void shouldReturnNotFound_WhenReceiptIdDoesNotExist() throws Exception {
        String nonExistentId = "non-existent-id";

        mockMvc.perform(get("/receipts/" + nonExistentId + "/points"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("No receipt found for that ID."));
    }

    @Test
    void shouldReturnBadRequest_WhenItemPriceIsInvalid() throws Exception {
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
                List.of(new Item("Pepsi 2L", "1.9")), "1.99");

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The receipt is invalid."));
    }

    @Test
    void shouldReturnBadRequest_WhenItemsListIsEmpty() throws Exception {
        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15", List.of(), "1.99");

        mockMvc.perform(post("/receipts/process")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(receipt)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("The receipt is invalid."));
    }
}