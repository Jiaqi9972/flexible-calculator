package org.ebay.flexiblecalculator.operation.strategy;


import org.ebay.flexiblecalculator.exception.CalculationException;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.OperationStrategy;
import org.springframework.stereotype.Component;

/**
 * Implementation of division operation strategy
 */
@Component
public class DivideOperation implements OperationStrategy {

    @Override
    public Number execute(Number num1, Number num2) {
        // Check for division by zero
        if (num2.doubleValue() == 0) {
            throw new CalculationException("Division by zero is not allowed");
        }

        // Calculate result with proper precision
        double exactResult = num1.doubleValue() / num2.doubleValue();

        // If result is a whole number and both inputs are integers, return an integer
        if (exactResult == Math.floor(exactResult) && !Double.isInfinite(exactResult) &&
                num1 instanceof Integer && num2 instanceof Integer) {
            return (int)exactResult;
        }

        // Otherwise follow the type hierarchy
        if (num1 instanceof Double || num2 instanceof Double) {
            return exactResult;
        } else if (num1 instanceof Float || num2 instanceof Float) {
            return (float)exactResult;
        } else if (num1 instanceof Long || num2 instanceof Long) {
            return exactResult == Math.floor(exactResult) ? (long)exactResult : exactResult;
        } else {
            return exactResult;
        }
    }

    @Override
    public Operation getSupportedOperation() {
        return Operation.DIVIDE;
    }
}