package org.ebay.flexiblecalculator.service.calculator;

import org.ebay.flexiblecalculator.model.Operation;

/**
 * Calculator interface defining basic calculation methods
 */
public interface Calculator {

    /**
     * Perform a single calculation between two numbers
     *
     * @param op the operation to perform
     * @param num1 first operand
     * @param num2 second operand
     * @return calculation result
     */
    Number calculate(Operation op, Number num1, Number num2);
}
