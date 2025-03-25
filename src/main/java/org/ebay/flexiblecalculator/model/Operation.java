package org.ebay.flexiblecalculator.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum defining basic calculator operations
 */
@Schema(description = "Available mathematical operations")
public enum Operation {
    @Schema(description = "Addition operation (+)")
    ADD,

    @Schema(description = "Subtraction operation (-)")
    SUBTRACT,

    @Schema(description = "Multiplication operation (*)")
    MULTIPLY,

    @Schema(description = "Division operation (/)")
    DIVIDE
}