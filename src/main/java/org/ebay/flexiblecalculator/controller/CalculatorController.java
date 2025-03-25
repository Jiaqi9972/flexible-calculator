package org.ebay.flexiblecalculator.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.ebay.flexiblecalculator.dto.request.CalculateRequest;
import org.ebay.flexiblecalculator.dto.request.ChainRequest;
import org.ebay.flexiblecalculator.service.CalculatorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for calculator operations
 */
@RestController
@Slf4j
@RequestMapping("/api/v1/calculator")
@Tag(name = "Calculator API", description = "API for performing basic and chained mathematical operations")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    /**
     * Basic calculation endpoint
     */
    @PostMapping("/calculate")
    @Operation(
            summary = "Perform a single calculation",
            description = "Performs a calculation between two numbers using the specified operation"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Calculation completed successfully",
                    content = @Content(schema = @Schema(implementation = Number.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or operation",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public Number calculate(
            @Parameter(description = "Calculation request with operation and operands", required = true)
            @Valid @RequestBody CalculateRequest calculateRequest) {
        log.info("CalculatorController: received calculate request={}", calculateRequest);
        return calculatorService.calculate(calculateRequest);
    }

    /**
     * Chain calculation endpoint
     */
    @PostMapping("/chain")
    @Operation(
            summary = "Perform a chain of calculations",
            description = "Performs a series of operations starting with an initial value and applying operations sequentially"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Chain calculation completed successfully",
                    content = @Content(schema = @Schema(implementation = Number.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or operation",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content)
    })
    public Number chainOperations(
            @Parameter(description = "Chain calculation request with initial value and sequence of operations", required = true)
            @Valid @RequestBody ChainRequest chainRequest) {
        log.info("CalculatorController: received chain request={}", chainRequest);
        log.info("CalculatorController: starting chained calculator");
        return calculatorService.calculateChain(chainRequest);
    }
}