package org.ebay.flexiblecalculator.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ebay.flexiblecalculator.model.Operation;

/**
 * Request model for basic calculation operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request model for performing a single calculation operation")
public class CalculateRequest {

    @Schema(description = "Mathematical operation to perform", required = true, example = "ADD")
    @NotNull(message = "Operation is required")
    private Operation operation;

    @Schema(description = "First operand for the calculation", required = true, example = "10")
    @NotNull(message = "First operand is required")
    private Number num1;

    @Schema(description = "Second operand for the calculation", required = true, example = "5")
    @NotNull(message = "Second operand is required")
    private Number num2;
}