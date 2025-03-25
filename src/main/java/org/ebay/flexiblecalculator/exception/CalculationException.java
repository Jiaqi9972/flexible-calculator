package org.ebay.flexiblecalculator.exception;

import java.io.Serial;

/**
 * Custom exception for calculator operation errors
 */
public class CalculationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public CalculationException(String message) {
        super(message);
    }

    public CalculationException(String message, Throwable cause) {
        super(message, cause);
    }
}
