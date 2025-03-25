package org.ebay.flexiblecalculator.unit.operation;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.OperationStrategy;
import org.ebay.flexiblecalculator.operation.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the OperationRegistry class
 */
class OperationRegistryTest {

    private OperationRegistry registry;
    private AddOperation addOperation;
    private SubtractOperation subtractOperation;
    private MultiplyOperation multiplyOperation;
    private DivideOperation divideOperation;

    @BeforeEach
    void setUp() {
        addOperation = new AddOperation();
        subtractOperation = new SubtractOperation();
        multiplyOperation = new MultiplyOperation();
        divideOperation = new DivideOperation();

        registry = new OperationRegistry(Arrays.asList(
                addOperation,
                subtractOperation,
                multiplyOperation,
                divideOperation
        ));
    }

    @Test
    @DisplayName("Should initialize registry with provided strategies")
    void shouldInitializeRegistryWithProvidedStrategies() {
        // Assert
        assertTrue(registry.supportsOperation(Operation.ADD));
        assertTrue(registry.supportsOperation(Operation.SUBTRACT));
        assertTrue(registry.supportsOperation(Operation.MULTIPLY));
        assertTrue(registry.supportsOperation(Operation.DIVIDE));
    }

    @Test
    @DisplayName("Should retrieve correct strategy for operation")
    void shouldRetrieveCorrectStrategyForOperation() {
        // Act
        Optional<OperationStrategy> addStrategy = registry.getStrategy(Operation.ADD);
        Optional<OperationStrategy> subtractStrategy = registry.getStrategy(Operation.SUBTRACT);
        Optional<OperationStrategy> multiplyStrategy = registry.getStrategy(Operation.MULTIPLY);
        Optional<OperationStrategy> divideStrategy = registry.getStrategy(Operation.DIVIDE);

        // Assert
        assertTrue(addStrategy.isPresent());
        assertTrue(subtractStrategy.isPresent());
        assertTrue(multiplyStrategy.isPresent());
        assertTrue(divideStrategy.isPresent());

        assertEquals(addOperation, addStrategy.get());
        assertEquals(subtractOperation, subtractStrategy.get());
        assertEquals(multiplyOperation, multiplyStrategy.get());
        assertEquals(divideOperation, divideStrategy.get());
    }

    @Test
    @DisplayName("Should dynamically register new operation strategy")
    void shouldDynamicallyRegisterNewOperationStrategy() {
        // Arrange
        // Create a custom operation strategy for testing
        OperationStrategy customStrategy = new OperationStrategy() {
            @Override
            public Number execute(Number num1, Number num2) {
                return num1.doubleValue() % num2.doubleValue(); // Modulo operation
            }

            @Override
            public Operation getSupportedOperation() {
                return Operation.ADD; // Override an existing operation for testing
            }
        };

        // Act
        registry.registerStrategy(customStrategy);
        Optional<OperationStrategy> retrievedStrategy = registry.getStrategy(Operation.ADD);

        // Assert
        assertTrue(retrievedStrategy.isPresent());
        assertEquals(customStrategy, retrievedStrategy.get());
        // Verify the original strategy was replaced
        assertNotEquals(addOperation, retrievedStrategy.get());
    }

    @Test
    @DisplayName("Should return empty optional for unsupported operation")
    void shouldReturnEmptyOptionalForUnsupportedOperation() {
        // Arrange - Create empty registry
        registry = new OperationRegistry(List.of());

        // Act
        Optional<OperationStrategy> strategy = registry.getStrategy(Operation.ADD);

        // Assert
        assertFalse(strategy.isPresent());
        assertFalse(registry.supportsOperation(Operation.ADD));
    }
}
