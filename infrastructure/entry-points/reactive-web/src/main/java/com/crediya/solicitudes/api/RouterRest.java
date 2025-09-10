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
    @RouterOperations({
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
                                @ApiResponse(responseCode = "201", description = "Application created successfully",
                                        content = @Content(schema = @Schema(implementation = com.crediya.solicitudes.api.dto.LoanApplicationResponse.class))),
                                @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input data",
                                        content = @Content(schema = @Schema(example = "{\"error\": \"Bad Request\", \"message\": \"customerDocument is required\"}"))),
                                @ApiResponse(responseCode = "500", description = "Internal Server Error",
                                        content = @Content(schema = @Schema(example = "{\"error\": \"Internal Server Error\", \"message\": \"An unexpected error occurred\"}")))                    }
                )
        ),
        @RouterOperation(
                path = "/api/v1/solicitud",
                method = RequestMethod.GET,
                operation = @Operation(
                        operationId = "listLoanApplications",
                        summary = "List loan applications for review",
                        description = "Returns paginated list of loan applications that need manual review (PENDING_REVIEW, REJECTED, MANUAL_REVIEW)",
                        tags = {"Loan Applications"},
                        responses = {
                                @ApiResponse(responseCode = "200", description = "Applications retrieved successfully",
                                        content = @Content(schema = @Schema(implementation = com.crediya.solicitudes.api.dto.PagedResponse.class))),
                                @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid or missing JWT token"),
                                @ApiResponse(responseCode = "403", description = "Forbidden - Insufficient permissions"),
                                @ApiResponse(responseCode = "500", description = "Internal Server Error")
                        }
                )
        )
    })
    public RouterFunction<ServerResponse> loanApplicationRoutes(LoanApplicationHandler handler) {
        return RouterFunctions
                .route(POST("/api/v1/solicitud").and(accept(MediaType.APPLICATION_JSON)), handler::create)
                .andRoute(GET("/api/v1/solicitud").and(accept(MediaType.APPLICATION_JSON)), handler::list);
    }

    @Bean
    public RouterFunction<ServerResponse> jwtConfigRoutes(JwtDebugHandler debugHandler) {
        return RouterFunctions
                .route(POST("/api/v1/config/disable-jwt").and(accept(MediaType.APPLICATION_JSON)), debugHandler::disableJwtValidation);
    }
}