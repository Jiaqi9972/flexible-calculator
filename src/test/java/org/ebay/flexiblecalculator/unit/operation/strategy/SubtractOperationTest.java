package org.ebay.flexiblecalculator.unit.operation.strategy;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.SubtractOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the SubtractOperation strategy
 */
class SubtractOperationTest {

    private SubtractOperation subtractOperation;

    @BeforeEach
    void setUp() {
        subtractOperation = new SubtractOperation();
    }

    @Test
    @DisplayName("Should subtract two positive integers correctly")
    void shouldSubtractTwoPositiveIntegers() {
        // Arrange
        Number num1 = 5;
        Number num2 = 3;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should subtract two negative integers correctly")
    void shouldSubtractTwoNegativeIntegers() {
        // Arrange
        Number num1 = -5;
        Number num2 = -3;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(-2, result);
    }

    @Test
    @DisplayName("Should handle negative result")
    void shouldHandleNegativeResult() {
        // Arrange
        Number num1 = 3;
        Number num2 = 5;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(-2, result);
    }

    @Test
    @DisplayName("Should subtract positive from negative correctly")
    void shouldSubtractPositiveFromNegative() {
        // Arrange
        Number num1 = -5;
        Number num2 = 3;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(-8, result);
    }

    @Test
    @DisplayName("Should subtract decimal numbers correctly")
    void shouldSubtractDecimalNumbers() {
        // Arrange
        Number num1 = 5.5;
        Number num2 = 3.3;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(2.2, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should handle zero as first operand")
    void shouldHandleZeroAsFirstOperand() {
        // Arrange
        Number num1 = 0;
        Number num2 = 5;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(-5, result);
    }

    @Test
    @DisplayName("Should handle zero as second operand")
    void shouldHandleZeroAsSecondOperand() {
        // Arrange
        Number num1 = 5;
        Number num2 = 0;

        // Act
        Number result = subtractOperation.execute(num1, num2);

        // Assert
        assertEquals(5, result);
    }

    @Test
    @DisplayName("Should return correct operation type")
    void shouldReturnCorrectOperationType() {
        assertEquals(Operation.SUBTRACT, subtractOperation.getSupportedOperation());
    }
}