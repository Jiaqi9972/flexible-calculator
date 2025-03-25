package org.ebay.flexiblecalculator.operation;

import org.ebay.flexiblecalculator.model.Operation;

/**
 * Operation strategy interface that defines calculation methods
 */
public interface OperationStrategy {
    /**
     * Execute calculation operation
     *
     * @param num1 first operand
     * @param num2 second operand
     * @return calculation result
     */
    Number execute(Number num1, Number num2);

    /**
     * Get the operation type supported by this strategy
     *
     * @return operation type
     */
    Operation getSupportedOperation();
}
