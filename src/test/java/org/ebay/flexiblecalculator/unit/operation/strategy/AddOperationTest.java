package org.ebay.flexiblecalculator.unit.operation.strategy;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.AddOperation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the AddOperation strategy
 */
class AddOperationTest {

    private AddOperation addOperation;

    @BeforeEach
    void setUp() {
        addOperation = new AddOperation();
    }

    @Test
    @DisplayName("Should add two positive integers correctly")
    void shouldAddTwoPositiveIntegers() {
        // Arrange
        Number num1 = 5;
        Number num2 = 3;

        // Act
        Number result = addOperation.execute(num1, num2);

        // Assert
        assertEquals(8, result);
    }

    @Test
    @DisplayName("Should add two negative integers correctly")
    void shouldAddTwoNegativeIntegers() {
        // Arrange
        Number num1 = -5;
        Number num2 = -3;

        // Act
        Number result = addOperation.execute(num1, num2);

        // Assert
        assertEquals(-8, result);
    }

    @Test
    @DisplayName("Should add mixed positive and negative integers correctly")
    void shouldAddMixedSignIntegers() {
        // Arrange
        Number num1 = 5;
        Number num2 = -3;

        // Act
        Number result = addOperation.execute(num1, num2);

        // Assert
        assertEquals(2, result);
    }

    @Test
    @DisplayName("Should add decimal numbers correctly")
    void shouldAddDecimalNumbers() {
        // Arrange
        Number num1 = 5.5;
        Number num2 = 3.3;

        // Act
        Number result = addOperation.execute(num1, num2);

        // Assert
        assertEquals(8.8, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should return correct operation type")
    void shouldReturnCorrectOperationType() {
        assertEquals(Operation.ADD, addOperation.getSupportedOperation());
    }
}