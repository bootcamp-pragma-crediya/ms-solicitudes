package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.constants.ErrorMessages;
import com.crediya.solicitudes.api.constants.LogMessages;
import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
import com.crediya.solicitudes.usecase.loanapplication.ListLoanApplicationsUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoanApplicationHandler {

    private final LoanApplicationUseCase useCase;
    private final ListLoanApplicationsUseCase listUseCase;
    private final LoanDtoMapper mapper;

    public Mono<ServerResponse> create(ServerRequest request) {
        String userId = request.headers().firstHeader("X-User-Id");
        
        if (userId == null || userId.isBlank()) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of("error", "Usuario no autenticado"));
        }
        
        String userEmail = (String) request.exchange().getAttributes().get("user.email");
        log.info("[Handler] User email from JWT: {}, userId: {}", userEmail, userId);
        
        return request.bodyToMono(CreateLoanRequestRequest.class)
                .doOnNext(req -> log.info("[Handler] POST request: userId={}, doc={}, amount={}, term={}",
                        userId, req.customerDocument(), req.amount(), req.termMonths()))
                .flatMap(req -> {
                    var domain = mapper.toDomain(req);
                    log.info("[Handler] Mapped domain: {}", domain);
                    return domain != null ? Mono.just(domain) : 
                           Mono.error(new com.crediya.solicitudes.model.exception.InvalidLoanApplicationException(
                               com.crediya.solicitudes.model.exception.ValidationMessage.BODY_REQUIRED.getMessage()));
                })
                .flatMap(domain -> {
                    log.info("[Handler] Calling useCase.execute");
                    return useCase.execute(domain, userId, userEmail);
                })
                .doOnNext(result -> log.info("[Handler] UseCase result: name={}, salary={}", result.getCustomerName(), result.getBaseSalary()))
                .map(mapper::toResponse)
                .doOnSuccess(resp -> log.info(LogMessages.HANDLER_CREATED, resp.id(), resp.status()))
                .flatMap(resp -> ServerResponse.created(URI.create("/api/v1/solicitud/" + resp.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(resp))
                .onErrorResume(this::handleError);
    }

    public Mono<ServerResponse> list(ServerRequest request) {
        int page = request.queryParam("page")
                .map(Integer::parseInt)
                .orElse(0);
        int size = request.queryParam("size")
                .map(Integer::parseInt)
                .orElse(10);
        
        log.info("Listing loan applications for review - page: {}, size: {}", page, size);
        
        return listUseCase.execute(page, size)
                .map(result -> {
                    var apps = mapper.toListResponse(result.getContent());
                    log.info("Retrieved {} applications for review", apps.size());
                    return java.util.Map.of(
                        "content", apps,
                        "page", result.getPage(),
                        "size", result.getSize(),
                        "total_elements", result.getTotalElements(),
                        "total_pages", result.getTotalPages()
                    );
                })
                .flatMap(resp -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(resp))
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable error) {
        log.error(LogMessages.HANDLER_ERROR, error.getMessage(), error);
        
        if (error instanceof com.crediya.solicitudes.model.exception.InvalidLoanApplicationException ||
            error instanceof com.crediya.solicitudes.model.exception.InvalidLoanTypeException) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of("error", ErrorMessages.BAD_REQUEST, "message", error.getMessage()));
        }
        
        if (error instanceof com.fasterxml.jackson.core.JsonProcessingException) {
            log.error("JSON serialization error: {}", error.getMessage());
            return ServerResponse.status(500)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of("error", "SERIALIZATION_ERROR", "message", "Error processing response"));
        }
        
        return ServerResponse.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(java.util.Map.of("error", ErrorMessages.INTERNAL_SERVER_ERROR, "message", ErrorMessages.UNEXPECTED_ERROR));
    }
}