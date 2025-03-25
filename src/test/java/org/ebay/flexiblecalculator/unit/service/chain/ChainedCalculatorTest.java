package org.ebay.flexiblecalculator.unit.service.chain;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.service.calculator.Calculator;
import org.ebay.flexiblecalculator.service.chain.ChainedCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the ChainedCalculator class
 */
@ExtendWith(MockitoExtension.class)
class ChainedCalculatorTest {

    @Mock
    private Calculator calculator;

    private ChainedCalculator chainedCalculator;

    @BeforeEach
    void setUp() {
        chainedCalculator = new ChainedCalculator(calculator);
    }

    @Test
    @DisplayName("Should start with initial value")
    void shouldStartWithInitialValue() {
        // Act
        Number result = chainedCalculator.start(10).getResult();

        // Assert
        assertEquals(10, result);
    }

    @Test
    @DisplayName("Should apply single operation correctly")
    void shouldApplySingleOperation() {
        // Arrange
        Number initialValue = 5;
        Number operand = 3;
        Number expectedResult = 8;

        when(calculator.calculate(Operation.ADD, initialValue, operand))
                .thenReturn(expectedResult);

        // Act
        Number result = chainedCalculator
                .start(initialValue)
                .apply(Operation.ADD, operand)
                .getResult();

        // Assert
        assertEquals(expectedResult, result);
        verify(calculator).calculate(Operation.ADD, initialValue, operand);
    }

    @Test
    @DisplayName("Should chain multiple operations correctly")
    void shouldChainMultipleOperations() {
        // Arrange
        Number initialValue = 5;
        Number addOperand = 3;
        Number multiplyOperand = 2;
        Number divideOperand = 4;

        Number afterAddResult = 8;
        Number afterMultiplyResult = 16;
        Number finalResult = 4;

        when(calculator.calculate(Operation.ADD, initialValue, addOperand))
                .thenReturn(afterAddResult);
        when(calculator.calculate(Operation.MULTIPLY, afterAddResult, multiplyOperand))
                .thenReturn(afterMultiplyResult);
        when(calculator.calculate(Operation.DIVIDE, afterMultiplyResult, divideOperand))
                .thenReturn(finalResult);

        // Act
        Number result = chainedCalculator
                .start(initialValue)
                .apply(Operation.ADD, addOperand)
                .apply(Operation.MULTIPLY, multiplyOperand)
                .apply(Operation.DIVIDE, divideOperand)
                .getResult();

        // Assert
        assertEquals(finalResult, result);
        verify(calculator).calculate(Operation.ADD, initialValue, addOperand);
        verify(calculator).calculate(Operation.MULTIPLY, afterAddResult, multiplyOperand);
        verify(calculator).calculate(Operation.DIVIDE, afterMultiplyResult, divideOperand);
    }

    @Test
    @DisplayName("Should handle complex calculation chain")
    void shouldHandleComplexCalculationChain() {
        // Arrange: 10 -> -5 -> *2 -> /3 = 3.333...
        Number initialValue = 10;
        Number subtractOperand = 5;
        Number multiplyOperand = 2;
        Number divideOperand = 3;

        Number afterSubtractResult = 5;
        Number afterMultiplyResult = 10;
        Number finalResult = 3.333;

        when(calculator.calculate(Operation.SUBTRACT, initialValue, subtractOperand))
                .thenReturn(afterSubtractResult);
        when(calculator.calculate(Operation.MULTIPLY, afterSubtractResult, multiplyOperand))
                .thenReturn(afterMultiplyResult);
        when(calculator.calculate(Operation.DIVIDE, afterMultiplyResult, divideOperand))
                .thenReturn(finalResult);

        // Act
        Number result = chainedCalculator
                .start(initialValue)
                .apply(Operation.SUBTRACT, subtractOperand)
                .apply(Operation.MULTIPLY, multiplyOperand)
                .apply(Operation.DIVIDE, divideOperand)
                .getResult();

        // Assert
        assertEquals(finalResult, result);
    }

    @Test
    @DisplayName("Should return initial value when no operations are applied")
    void shouldReturnInitialValueWhenNoOperationsApplied() {
        // Arrange
        Number initialValue = 10;

        // Act
        Number result = chainedCalculator
                .start(initialValue)
                .getResult();

        // Assert
        assertEquals(initialValue, result);
        verifyNoInteractions(calculator);
    }
}
