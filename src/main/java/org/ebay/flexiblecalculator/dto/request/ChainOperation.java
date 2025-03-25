package org.ebay.flexiblecalculator.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ebay.flexiblecalculator.model.Operation;

/**
 * Single operation in a calculation chain
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Represents a single operation in a calculation chain")
public class ChainOperation {

    @Schema(description = "Operation to apply", required = true, example = "ADD")
    @NotNull(message = "Operation is required")
    private Operation operation;

    @Schema(description = "Value to apply with the operation", required = true, example = "5")
    @NotNull(message = "Operation value is required")
    private Number value;
}