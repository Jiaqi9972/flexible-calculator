package org.ebay.flexiblecalculator.operation.strategy;

import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.OperationStrategy;
import org.springframework.stereotype.Component;

/**
 * Implementation of addition operation strategy
 */
@Component
public class AddOperation implements OperationStrategy {

    @Override
    public Number execute(Number num1, Number num2) {
        if (num1 instanceof Double || num2 instanceof Double) {
            return num1.doubleValue() + num2.doubleValue();
        } else if (num1 instanceof Float || num2 instanceof Float) {
            return num1.floatValue() + num2.floatValue();
        } else if (num1 instanceof Long || num2 instanceof Long) {
            return num1.longValue() + num2.longValue();
        } else {
            return num1.intValue() + num2.intValue();
        }
    }

    @Override
    public Operation getSupportedOperation() {
        return Operation.ADD;
    }
}