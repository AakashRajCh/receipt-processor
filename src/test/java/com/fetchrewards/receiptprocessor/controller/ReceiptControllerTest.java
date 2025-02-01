////package com.fetchrewards.receiptprocessor.controller;
////
////import com.fasterxml.jackson.databind.ObjectMapper;
////import com.fetchrewards.receiptprocessor.model.Item;
////import com.fetchrewards.receiptprocessor.model.Receipt;
////import com.fetchrewards.receiptprocessor.service.ReceiptService;
////import org.junit.jupiter.api.Test;
////import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
////import org.springframework.boot.test.context.SpringBootTest;
////import org.springframework.http.MediaType;
////import org.springframework.test.web.servlet.MockMvc;
////
////import java.util.List;
////
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
////import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
////import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
////
////@SpringBootTest
////@AutoConfigureMockMvc
////public class ReceiptControllerTest {
////
////    @Autowired
////    private MockMvc mockMvc;
////
////    @Autowired
////    private ObjectMapper objectMapper;
////
////    @Autowired
////    private ReceiptService receiptService;
////
////    @Test
////    void shouldReturnReceiptId_WhenProcessingValidReceipt() throws Exception {
////        // Arrange
////        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
////                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
////
////        // Act & Assert
////        mockMvc.perform(post("/receipts/process")
////                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(objectMapper.writeValueAsString(receipt)))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.id").exists());
////    }
////
////    @Test
////    void shouldReturnPoints_WhenReceiptIdIsValid() throws Exception {
////        // Arrange
////        Receipt receipt = new Receipt("Walmart", "2022-06-15", "14:15",
////                List.of(new Item("Pepsi 2L", "1.99")), "1.99");
////        String id = receiptService.processReceipt(receipt);
////
////        // Act & Assert
////        mockMvc.perform(get("/receipts/" + id + "/points"))
////                .andExpect(status().isOk())
////                .andExpect(jsonPath("$.points").exists());
////    }
////
////    @Test
////    void shouldReturnNotFound_WhenReceiptIdIsInvalid() throws Exception {
////        // Act & Assert
////        mockMvc.perform(get("/receipts/invalid-id/points"))
////                .andExpect(status().isNotFound());
////    }
////
////}
//
//
//
//package com.fetchrewards.receiptprocessor.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fetchrewards.receiptprocessor.model.Item;
//import com.fetchrewards.receiptprocessor.model.Receipt;
//import com.fetchrewards.receiptprocessor.service.ReceiptService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.util.List;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class ReceiptControllerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private ReceiptService receiptService;
//
//    private Receipt validReceipt;
//
//    @BeforeEach
//    void setup() {
//        validReceipt = new Receipt(
//                "Walmart",
//                "2022-06-15",
//                "14:15",
//                List.of(new Item("Pepsi 2L", "1.99")),
//                "1.99"
//        );
//    }
//
//    @Test
//    void shouldReturnCreatedStatus_WhenProcessingValidReceipt() throws Exception {
//        mockMvc.perform(post("/receipts/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(validReceipt)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").exists());
//    }
//
//    @Test
//    void shouldReturnBadRequest_WhenMissingRequiredFields() throws Exception {
//        // Arrange - Missing purchaseDate
//        Receipt invalidReceipt = new Receipt(
//                "Walmart",
//                null,  // Missing date
//                "14:15",
//                List.of(new Item("Pepsi 2L", "1.99")),
//                "1.99"
//        );
//
//        // Act & Assert
//        mockMvc.perform(post("/receipts/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidReceipt)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.purchaseDate").exists());  // Ensure validation error message is returned
//    }
//
//    @Test
//    void shouldReturnBadRequest_WhenInvalidPriceFormat() throws Exception {
//        // Arrange - Invalid price format
//        Receipt invalidReceipt = new Receipt(
//                "Walmart",
//                "2022-06-15",
//                "14:15",
//                List.of(new Item("Pepsi 2L", "1.999")), // Incorrect price format
//                "1.99"
//        );
//
//        // Act & Assert
//        mockMvc.perform(post("/receipts/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(invalidReceipt)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.items[0].price").exists());  // Ensure error for item price
//    }
//
//    @Test
//    void shouldReturnPoints_WhenReceiptIdIsValid() throws Exception {
//        // Arrange
//        String id = receiptService.processReceipt(validReceipt);
//
//        // Act & Assert
//        mockMvc.perform(get("/receipts/" + id + "/points"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.points").exists());
//    }
//
//    @Test
//    void shouldReturnNotFound_WhenReceiptIdIsInvalid() throws Exception {
//        mockMvc.perform(get("/receipts/invalid-id/points"))
//                .andExpect(status().isNotFound())
//                .andExpect(jsonPath("$.error").value("No receipt found for that ID."));
//    }
//
//    @Test
//    void shouldReturnInternalServerError_WhenUnexpectedErrorOccurs() throws Exception {
//        // Simulate server error by sending invalid JSON
//        String invalidJson = "{ \"store\": \"Walmart\", \"purchaseDate\": \"2022-06-15\", \"purchaseTime\": \"14:15\", \"items\": [ { \"shortDescription\": \"Pepsi 2L\", \"price\": \"1.99\" ] } }";
//
//        mockMvc.perform(post("/receipts/process")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidJson))
//                .andExpect(status().isInternalServerError())
//                .andExpect(jsonPath("$.error").value("An unexpected error occurred. Please try again later."));
//    }
//}