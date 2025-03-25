package org.ebay.flexiblecalculator.service.calculator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.exception.CalculationException;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.strategy.OperationRegistry;
import org.springframework.stereotype.Service;

/**
 * Implementation of Calculator interface
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CalculatorImpl implements Calculator {

    private final OperationRegistry operationRegistry;

    @Override
    public Number calculate(Operation op, Number num1, Number num2) {
        log.info("Received operation: {}, num1: {}, num2: {}", op, num1, num2);

        return operationRegistry.getStrategy(op)
                .orElseThrow(() -> {
                    log.error("Unsupported operation: {}", op);
                    return new CalculationException("Unsupported operation: " + op);
                })
                .execute(num1, num2);
    }
}
