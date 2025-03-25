package org.ebay.flexiblecalculator.service.chain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.service.calculator.Calculator;
import org.springframework.stereotype.Service;

/**
 * Service for chaining multiple calculation operations
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChainedCalculator {

    private final Calculator calculator;
    private Number currentValue;

    /**
     * Start a calculation chain with initial value
     *
     * @param initialValue the starting value
     * @return this instance for chaining
     */
    public ChainedCalculator start(Number initialValue) {
        this.currentValue = initialValue;
        log.info("Starting calculation chain with initial value: {}", initialValue);
        return this;
    }

    /**
     * Apply an operation to the current value
     *
     * @param operation the operation to apply
     * @param operand the operand value
     * @return this instance for chaining
     */
    public ChainedCalculator apply(Operation operation, Number operand) {
        log.info("ChainedCalculator: applying operation: {}, operand: {}", operation, operand);
        currentValue = calculator.calculate(operation, currentValue, operand);
        log.debug("New current value: {}", currentValue);
        return this;
    }

    /**
     * Get the final result of the calculation chain
     *
     * @return the calculation result
     */
    public Number getResult() {
        log.info("Returning final result: {}", currentValue);
        return currentValue;
    }
}
