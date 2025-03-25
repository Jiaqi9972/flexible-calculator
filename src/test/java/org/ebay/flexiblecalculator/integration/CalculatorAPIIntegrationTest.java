package org.ebay.flexiblecalculator.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ebay.flexiblecalculator.dto.request.CalculateRequest;
import org.ebay.flexiblecalculator.dto.request.ChainOperation;
import org.ebay.flexiblecalculator.dto.request.ChainRequest;
import org.ebay.flexiblecalculator.model.Operation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test for calculator API endpoints
 * Tests the complete request flow from controller through service to implementation
 */
@SpringBootTest
@AutoConfigureMockMvc
public class CalculatorAPIIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should process basic calculation request through API")
    void shouldProcessBasicCalculationRequest() throws Exception {
        // Create a calculation request
        CalculateRequest request = new CalculateRequest(Operation.ADD, 5, 3);

        // Send request and verify response
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(8));
    }

    @Test
    @DisplayName("Should process chain calculation request through API")
    void shouldProcessChainCalculationRequest() throws Exception {
        // Create a chain calculation request
        ChainRequest request = new ChainRequest(
                10,
                Arrays.asList(
                        new ChainOperation(Operation.ADD, 5),
                        new ChainOperation(Operation.MULTIPLY, 2),
                        new ChainOperation(Operation.SUBTRACT, 5)
                )
        );

        // Send request and verify response
        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(25));
    }

    @Test
    @DisplayName("Should handle division by zero error")
    void shouldHandleDivisionByZeroError() throws Exception {
        // Create a request with division by zero
        CalculateRequest request = new CalculateRequest(Operation.DIVIDE, 5, 0);

        // Send request and verify error response
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Division by zero is not allowed"));
    }

    @Test
    @DisplayName("Should handle invalid operation error")
    void shouldHandleInvalidOperationError() throws Exception {
        // Create invalid JSON with invalid operation
        String invalidJson = "{\"operation\":\"INVALID\",\"num1\":5,\"num2\":3}";

        // Send request and verify error response
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    @DisplayName("Should handle decimal operations correctly")
    void shouldHandleDecimalOperations() throws Exception {
        // Create a calculation request with decimal values
        CalculateRequest request = new CalculateRequest(Operation.MULTIPLY, 2.5, 3.5);

        // Send request and verify response
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(8.75));
    }

    @Test
    @DisplayName("Should handle complex chain calculation with mixed operations")
    void shouldHandleComplexChainCalculation() throws Exception {
        // Create a complex chain request
        ChainRequest request = new ChainRequest(
                100,
                Arrays.asList(
                        new ChainOperation(Operation.DIVIDE, 4),    // 100/4 = 25
                        new ChainOperation(Operation.ADD, 15),      // 25+15 = 40
                        new ChainOperation(Operation.SUBTRACT, 5),  // 40-5 = 35
                        new ChainOperation(Operation.MULTIPLY, 2)   // 35*2 = 70
                )
        );

        // Send request and verify response
        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(70));
    }
}
