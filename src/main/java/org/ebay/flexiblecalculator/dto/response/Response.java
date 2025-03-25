package org.ebay.flexiblecalculator.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Standard API response model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Standard API response wrapper")
public class Response {

    @Schema(description = "Response code indicating the status of the operation", example = "200")
    private String code;

    @Schema(description = "Human-readable message describing the result", example = "Calculation successful")
    private String message;

    @Schema(description = "Response payload, typically the result of the operation")
    private Object data;
}