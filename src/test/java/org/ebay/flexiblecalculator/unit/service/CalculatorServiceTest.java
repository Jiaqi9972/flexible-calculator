package org.ebay.flexiblecalculator.unit.service;

import org.ebay.flexiblecalculator.dto.request.CalculateRequest;
import org.ebay.flexiblecalculator.dto.request.ChainOperation;
import org.ebay.flexiblecalculator.dto.request.ChainRequest;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.service.CalculatorService;
import org.ebay.flexiblecalculator.service.calculator.Calculator;
import org.ebay.flexiblecalculator.service.chain.ChainedCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CalculatorService class
 */
@ExtendWith(MockitoExtension.class)
class CalculatorServiceTest {

    @Mock
    private Calculator calculator;

    @Mock
    private ChainedCalculator chainedCalculator;

    private CalculatorService calculatorService;

    @BeforeEach
    void setUp() {
        calculatorService = new CalculatorService(calculator, chainedCalculator);
    }

    @Test
    @DisplayName("Should process single calculation request")
    void shouldProcessSingleCalculationRequest() {
        // Arrange
        CalculateRequest request = new CalculateRequest(Operation.ADD, 5, 3);
        Number expectedResult = 8;

        when(calculator.calculate(Operation.ADD, 5, 3)).thenReturn(expectedResult);

        // Act
        Number result = calculatorService.calculate(request);

        // Assert
        assertEquals(expectedResult, result);
        verify(calculator).calculate(Operation.ADD, 5, 3);
    }

    @Test
    @DisplayName("Should process chain calculation request with one operation")
    void shouldProcessChainCalculationRequestWithOneOperation() {
        // Arrange
        Number initialValue = 5;
        Number operand = 3;
        Number expectedResult = 8;

        ChainOperation chainOperation = new ChainOperation(Operation.ADD, operand);
        ChainRequest request = new ChainRequest(initialValue, Collections.singletonList(chainOperation));

        when(chainedCalculator.start(initialValue)).thenReturn(chainedCalculator);
        when(chainedCalculator.apply(Operation.ADD, operand)).thenReturn(chainedCalculator);
        when(chainedCalculator.getResult()).thenReturn(expectedResult);

        // Act
        Number result = calculatorService.calculateChain(request);

        // Assert
        assertEquals(expectedResult, result);
        verify(chainedCalculator).start(initialValue);
        verify(chainedCalculator).apply(Operation.ADD, operand);
        verify(chainedCalculator).getResult();
    }

    @Test
    @DisplayName("Should process chain calculation request with multiple operations")
    void shouldProcessChainCalculationRequestWithMultipleOperations() {
        // Arrange
        Number initialValue = 5;
        List<ChainOperation> operations = Arrays.asList(
                new ChainOperation(Operation.ADD, 3),
                new ChainOperation(Operation.MULTIPLY, 2),
                new ChainOperation(Operation.DIVIDE, 4)
        );
        Number expectedResult = 4;

        ChainRequest request = new ChainRequest(initialValue, operations);

        when(chainedCalculator.start(initialValue)).thenReturn(chainedCalculator);
        when(chainedCalculator.apply(any(Operation.class), any(Number.class))).thenReturn(chainedCalculator);
        when(chainedCalculator.getResult()).thenReturn(expectedResult);

        // Act
        Number result = calculatorService.calculateChain(request);

        // Assert
        assertEquals(expectedResult, result);
        verify(chainedCalculator).start(initialValue);
        verify(chainedCalculator, times(3)).apply(any(Operation.class), any(Number.class));
        verify(chainedCalculator).apply(Operation.ADD, 3);
        verify(chainedCalculator).apply(Operation.MULTIPLY, 2);
        verify(chainedCalculator).apply(Operation.DIVIDE, 4);
        verify(chainedCalculator).getResult();
    }

    @Test
    @DisplayName("Should process chain calculation request with no operations")
    void shouldProcessChainCalculationRequestWithNoOperations() {
        // Arrange
        Number initialValue = 5;
        List<ChainOperation> operations = Collections.emptyList();
        ChainRequest request = new ChainRequest(initialValue, operations);

        when(chainedCalculator.start(initialValue)).thenReturn(chainedCalculator);
        when(chainedCalculator.getResult()).thenReturn(initialValue);

        // Act
        Number result = calculatorService.calculateChain(request);

        // Assert
        assertEquals(initialValue, result);
        verify(chainedCalculator).start(initialValue);
        verify(chainedCalculator, never()).apply(any(Operation.class), any(Number.class));
        verify(chainedCalculator).getResult();
    }
}