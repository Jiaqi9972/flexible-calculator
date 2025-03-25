package org.ebay.flexiblecalculator.operation.strategy;

import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.model.Operation;
import org.ebay.flexiblecalculator.operation.OperationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Registry for operation strategies that manages available operations
 */
@Component
@Slf4j
public class OperationRegistry {

    private final Map<Operation, OperationStrategy> strategies = new HashMap<>();

    /**
     * Initialize registry with available operation strategies via Spring dependency injection
     */
    @Autowired
    public OperationRegistry(List<OperationStrategy> operationStrategies) {
        operationStrategies.forEach(strategy -> {
            strategies.put(strategy.getSupportedOperation(), strategy);
            log.info("Registered operation strategy: {}", strategy.getSupportedOperation());
        });
    }

    /**
     * Get operation strategy for specified operation
     *
     * @param operation the operation to lookup
     * @return optional containing the strategy or empty if not found
     */
    public Optional<OperationStrategy> getStrategy(Operation operation) {
        return Optional.ofNullable(strategies.get(operation));
    }

    /**
     * Register a new operation strategy
     *
     * @param strategy the strategy to register
     */
    public void registerStrategy(OperationStrategy strategy) {
        strategies.put(strategy.getSupportedOperation(), strategy);
        log.info("Dynamically registered new operation strategy: {}", strategy.getSupportedOperation());
    }

    /**
     * Check if operation is supported
     *
     * @param operation the operation to check
     * @return true if supported, false otherwise
     */
    public boolean supportsOperation(Operation operation) {
        return strategies.containsKey(operation);
    }
}
