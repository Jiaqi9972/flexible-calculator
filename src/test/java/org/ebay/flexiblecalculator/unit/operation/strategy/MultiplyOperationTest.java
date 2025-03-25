package org.ebay.flexiblecalculator.unit.operation.strategy;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.MultiplyOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the MultiplyOperation strategy
 */
class MultiplyOperationTest {

    private MultiplyOperation multiplyOperation;

    @BeforeEach
    void setUp() {
        multiplyOperation = new MultiplyOperation();
    }

    @Test
    @DisplayName("Should multiply two positive integers correctly")
    void shouldMultiplyTwoPositiveIntegers() {
        // Arrange
        Number num1 = 5;
        Number num2 = 3;

        // Act
        Number result = multiplyOperation.execute(num1, num2);

        // Assert
        assertEquals(15, result);
    }

    @Test
    @DisplayName("Should multiply two negative integers correctly")
    void shouldMultiplyTwoNegativeIntegers() {
        // Arrange
        Number num1 = -5;
        Number num2 = -3;

        // Act
        Number result = multiplyOperation.execute(num1, num2);

        // Assert
        assertEquals(15, result);
    }

    @Test
    @DisplayName("Should multiply positive and negative integers correctly")
    void shouldMultiplyPositiveAndNegativeIntegers() {
        // Arrange
        Number num1 = 5;
        Number num2 = -3;

        // Act
        Number result = multiplyOperation.execute(num1, num2);

        // Assert
        assertEquals(-15, result);
    }

    @Test
    @DisplayName("Should multiply decimal numbers correctly")
    void shouldMultiplyDecimalNumbers() {
        // Arrange
        Number num1 = 5.5;
        Number num2 = 2.0;

        // Act
        Number result = multiplyOperation.execute(num1, num2);

        // Assert
        assertEquals(11.0, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should multiply by zero correctly")
    void shouldMultiplyByZeroCorrectly() {
        // Arrange
        Number num1 = 5;
        Number num2 = 0;

        // Act
        Number result = multiplyOperation.execute(num1, num2);

        // Assert
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Should return correct operation type")
    void shouldReturnCorrectOperationType() {
        assertEquals(Operation.MULTIPLY, multiplyOperation.getSupportedOperation());
    }
}