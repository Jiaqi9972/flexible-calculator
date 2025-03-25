package org.ebay.flexiblecalculator.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.ebay.flexiblecalculator.controller.CalculatorController;
import org.ebay.flexiblecalculator.dto.request.CalculateRequest;
import org.ebay.flexiblecalculator.dto.request.ChainOperation;
import org.ebay.flexiblecalculator.dto.request.ChainRequest;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.service.CalculatorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit tests for the CalculatorController
 */
@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CalculatorService calculatorService;

    @Test
    @DisplayName("Should process single calculation request correctly")
    void shouldProcessSingleCalculationRequestCorrectly() throws Exception {
        // Arrange
        CalculateRequest request = new CalculateRequest(Operation.ADD, 5, 3);
        when(calculatorService.calculate(any(CalculateRequest.class))).thenReturn(8);

        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(8));
    }

    @Test
    @DisplayName("Should process chain calculation request correctly")
    void shouldProcessChainCalculationRequestCorrectly() throws Exception {
        // Arrange
        ChainRequest request = new ChainRequest(
                10,
                Arrays.asList(
                        new ChainOperation(Operation.ADD, 5),
                        new ChainOperation(Operation.MULTIPLY, 2)
                )
        );
        when(calculatorService.calculateChain(any(ChainRequest.class))).thenReturn(30);

        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/chain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("200"))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data").value(30));
    }

    @Test
    @DisplayName("Should handle invalid request body")
    void shouldHandleInvalidRequestBody() throws Exception {
        // Arrange - invalid JSON
        String invalidJson = "{\"operation\":\"INVALID\",\"num1\":5,\"num2\":3}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/calculator/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

}