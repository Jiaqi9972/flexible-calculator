package org.ebay.flexiblecalculator.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.dto.request.CalculateRequest;
import org.ebay.flexiblecalculator.dto.request.ChainOperation;
import org.ebay.flexiblecalculator.dto.request.ChainRequest;
import org.ebay.flexiblecalculator.service.calculator.Calculator;
import org.ebay.flexiblecalculator.service.chain.ChainedCalculator;
import org.springframework.stereotype.Service;

/**
 * Service for handling calculation requests
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CalculatorService {

    private final Calculator calculator;
    private final ChainedCalculator chainedCalculator;
    /**
     * Process a single calculation request
     *
     * @param request the calculation request
     * @return calculation result
     */
    public Number calculate(CalculateRequest request) {
        log.info("Processing calculation request: {}", request);
        return calculator.calculate(request.getOperation(), request.getNum1(), request.getNum2());
    }

    /**
     * Process a chain calculation request
     *
     * @param request the chain request with initial value and operations
     * @return final calculation result
     */
    public Number calculateChain(ChainRequest request) {
        log.info("Processing chain calculation request with initial value: {}", request.getInitialValue());

        chainedCalculator.start(request.getInitialValue());

        for (ChainOperation operation : request.getOperations()) {
            chainedCalculator.apply(operation.getOperation(), operation.getValue());
        }

        return chainedCalculator.getResult();
    }
}