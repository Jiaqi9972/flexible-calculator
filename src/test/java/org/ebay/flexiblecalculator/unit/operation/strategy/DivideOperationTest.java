package org.ebay.flexiblecalculator.unit.operation.strategy;

import org.ebay.flexiblecalculator.exception.CalculationException;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.DivideOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the DivideOperation strategy
 */
class DivideOperationTest {

    private DivideOperation divideOperation;

    @BeforeEach
    void setUp() {
        divideOperation = new DivideOperation();
    }

    @Test
    @DisplayName("Should divide two positive integers correctly")
    void shouldDivideTwoPositiveIntegers() {
        // Arrange
        Number num1 = 6;
        Number num2 = 3;

        // Act
        Number result = divideOperation.execute(num1, num2);

        // Assert
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should divide two negative integers correctly")
    void shouldDivideTwoNegativeIntegers() {
        // Arrange
        Number num1 = -6;
        Number num2 = -3;

        // Act
        Number result = divideOperation.execute(num1, num2);

        // Assert
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should divide decimal numbers correctly")
    void shouldDivideDecimalNumbers() {
        // Arrange
        Number num1 = 5.0;
        Number num2 = 2.0;

        // Act
        Number result = divideOperation.execute(num1, num2);

        // Assert
        assertEquals(2.5, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should handle division resulting in decimal")
    void shouldHandleDivisionResultingInDecimal() {
        // Arrange
        Number num1 = 10;
        Number num2 = 3;

        // Act
        Number result = divideOperation.execute(num1, num2);

        // Assert
        assertEquals(3.333, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should throw exception when dividing by zero")
    void shouldThrowExceptionWhenDividingByZero() {
        // Arrange
        Number num1 = 5;
        Number num2 = 0;

        // Act & Assert
        assertThrows(CalculationException.class, () -> divideOperation.execute(num1, num2));
    }

    @Test
    @DisplayName("Should return correct operation type")
    void shouldReturnCorrectOperationType() {
        assertEquals(Operation.DIVIDE, divideOperation.getSupportedOperation());
    }
}