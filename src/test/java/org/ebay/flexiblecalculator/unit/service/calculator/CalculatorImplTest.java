package org.ebay.flexiblecalculator.unit.service.calculator;

import org.ebay.flexiblecalculator.exception.CalculationException;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.OperationStrategy;
import org.ebay.flexiblecalculator.operation.strategy.AddOperation;
import org.ebay.flexiblecalculator.operation.strategy.OperationRegistry;
import org.ebay.flexiblecalculator.service.calculator.CalculatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the CalculatorImpl class
 */
@ExtendWith(MockitoExtension.class)
class CalculatorImplTest {

    @Mock
    private OperationRegistry operationRegistry;

    @Mock
    private OperationStrategy operationStrategy;

    private CalculatorImpl calculator;

    @BeforeEach
    void setUp() {
        calculator = new CalculatorImpl(operationRegistry);
    }

    @Test
    @DisplayName("Should perform calculation using the correct operation strategy")
    void shouldPerformCalculationUsingCorrectStrategy() {
        // Arrange
        Number num1 = 5;
        Number num2 = 3;
        Number expectedResult = 8;

        when(operationRegistry.getStrategy(Operation.ADD))
                .thenReturn(Optional.of(operationStrategy));
        when(operationStrategy.execute(num1, num2)).thenReturn(expectedResult);

        // Act
        Number result = calculator.calculate(Operation.ADD, num1, num2);

        // Assert
        assertEquals(expectedResult, result);
        verify(operationRegistry).getStrategy(Operation.ADD);
        verify(operationStrategy).execute(num1, num2);
    }

    @Test
    @DisplayName("Should throw exception for unsupported operation")
    void shouldThrowExceptionForUnsupportedOperation() {
        // Arrange
        when(operationRegistry.getStrategy(Operation.ADD)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(CalculationException.class, () ->
                calculator.calculate(Operation.ADD, 5, 3));

        verify(operationRegistry).getStrategy(Operation.ADD);
    }

    @Test
    @DisplayName("Should handle the strategy's exception")
    void shouldHandleStrategyException() {
        // Arrange
        when(operationRegistry.getStrategy(Operation.DIVIDE))
                .thenReturn(Optional.of(operationStrategy));
        when(operationStrategy.execute(anyInt(), anyInt()))
                .thenThrow(new CalculationException("Division by zero"));

        // Act & Assert
        assertThrows(CalculationException.class, () ->
                calculator.calculate(Operation.DIVIDE, 5, 0));

        verify(operationRegistry).getStrategy(Operation.DIVIDE);
    }

    @Test
    @DisplayName("Should delegate to real operation for integration test")
    void shouldDelegateToRealOperation() {
        // Arrange - using a real AddOperation for this test
        AddOperation addOperation = new AddOperation();
        when(operationRegistry.getStrategy(Operation.ADD))
                .thenReturn(Optional.of(addOperation));

        // Act
        Number result = calculator.calculate(Operation.ADD, 5, 3);

        // Assert
        assertEquals(8, result);
        verify(operationRegistry).getStrategy(Operation.ADD);
    }
}