package org.ebay.flexiblecalculator.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Request model for chained calculation operations
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request model for a sequence of operations applied in order")
public class ChainRequest {

    @Schema(description = "Initial value for the calculation chain", required = true, example = "10")
    @NotNull(message = "Initial value is required")
    private Number initialValue;

    @Schema(description = "List of operations to apply in sequence", required = true)
    @NotNull(message = "Operations list is required")
    @Size(min = 1, message = "At least one operation is required")
    private List<ChainOperation> operations;
}