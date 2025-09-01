package com.crediya.solicitudes.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
@RequiredArgsConstructor
@Tag(name = "Loan Applications", description = "Credit loan application requests")
public class RouterRest {

    @Bean
    @RouterOperation(
            path = "/api/v1/solicitud",
            method = RequestMethod.POST,
            operation = @Operation(
                    operationId = "createLoanApplication",
                    summary = "Create a loan application",
                    description = "Validates customer and loan info, sets initial status to PENDING_REVIEW and persists the application",
                    tags = {"Loan Applications"},
                    requestBody = @RequestBody(
                            required = true,
                            content = @Content(schema = @Schema(implementation = com.crediya.solicitudes.api.dto.CreateLoanRequestRequest.class))
                    ),
                    responses = {
                            @ApiResponse(responseCode = "201", description = "Application created",
                                    content = @Content(schema = @Schema(implementation = com.crediya.solicitudes.api.dto.LoanApplicationResponse.class))),
                            @ApiResponse(responseCode = "400", description = "Invalid input", content = @Content),
                            @ApiResponse(responseCode = "500", description = "Unexpected error", content = @Content)
                    }
            )
    )
    public RouterFunction<ServerResponse> loanApplicationRoutes(LoanApplicationHandler handler) {
        return RouterFunctions
                .route(POST("/api/v1/solicitud").and(accept(MediaType.APPLICATION_JSON)), handler::create);
    }
}