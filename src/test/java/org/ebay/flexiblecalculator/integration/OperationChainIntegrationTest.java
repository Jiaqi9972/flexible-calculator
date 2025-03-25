package org.ebay.flexiblecalculator.integration;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.OperationRegistry;
import org.ebay.flexiblecalculator.service.calculator.Calculator;
import org.ebay.flexiblecalculator.service.chain.ChainedCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration test for operation chaining functionality
 * Tests the complete chain from Calculator to OperationRegistry to Strategy implementations
 */
@SpringBootTest
public class OperationChainIntegrationTest {

    @Autowired
    private Calculator calculator;

    @Autowired
    private ChainedCalculator chainedCalculator;

    @Autowired
    private OperationRegistry operationRegistry;

    @Test
    @DisplayName("Should perform a chain of operations correctly")
    void shouldPerformOperationChainCorrectly() {
        // Starting with 10
        // Add 5 = 15
        // Multiply by 2 = 30
        // Subtract 5 = 25
        // Divide by 5 = 5

        Number result = chainedCalculator
                .start(10)
                .apply(Operation.ADD, 5)
                .apply(Operation.MULTIPLY, 2)
                .apply(Operation.SUBTRACT, 5)
                .apply(Operation.DIVIDE, 5)
                .getResult();

        assertEquals(5, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should handle decimal values in chain operations")
    void shouldHandleDecimalValuesInChainOperations() {
        // Starting with 10.5
        // Add 5.5 = 16
        // Multiply by 0.5 = 8
        // Divide by 2.5 = 3.2

        Number result = chainedCalculator
                .start(10.5)
                .apply(Operation.ADD, 5.5)
                .apply(Operation.MULTIPLY, 0.5)
                .apply(Operation.DIVIDE, 2.5)
                .getResult();

        assertEquals(3.2, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should handle negative values in chain operations")
    void shouldHandleNegativeValuesInChainOperations() {
        // Starting with -10
        // Subtract -15 = 5
        // Multiply by -2 = -10
        // Divide by -2 = 5

        Number result = chainedCalculator
                .start(-10)
                .apply(Operation.SUBTRACT, -15)
                .apply(Operation.MULTIPLY, -2)
                .apply(Operation.DIVIDE, -2)
                .getResult();

        assertEquals(5, result.doubleValue(), 0.001);
    }

    @Test
    @DisplayName("Should integrate with individual operations correctly")
    void shouldIntegrateWithIndividualOperationsCorrectly() {
        // Test individual operations to verify they're working correctly in integration
        assertEquals(8, calculator.calculate(Operation.ADD, 5, 3));
        assertEquals(2, calculator.calculate(Operation.SUBTRACT, 5, 3));
        assertEquals(15, calculator.calculate(Operation.MULTIPLY, 5, 3));
        assertEquals(1.667, calculator.calculate(Operation.DIVIDE, 5, 3).doubleValue(), 0.001);
    }

}