package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.dto.CreateLoanRequestRequest;
import com.crediya.solicitudes.api.dto.LoanApplicationResponse;
import com.crediya.solicitudes.api.dto.LoanDtoMapper;
import com.crediya.solicitudes.usecase.loanapplication.LoanApplicationUseCase;
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
    private final LoanDtoMapper mapper;

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(CreateLoanRequestRequest.class)
                .doOnNext(req -> log.info("[Handler] POST /api/v1/solicitud doc={} type={} amount={} term={}",
                        req.customerDocument(), req.loanType(), req.amount(), req.termMonths()))
                .flatMap(req -> useCase.execute(mapper.toDomain(req)))
                .map(mapper::toResponse)
                .doOnSuccess(resp -> log.info("[Handler] Created id={} status={}", resp.id(), resp.status()))
                .flatMap(resp -> ServerResponse.created(URI.create("/api/v1/solicitud/" + resp.id()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(resp))
                .onErrorResume(this::handleError);
    }

    private Mono<ServerResponse> handleError(Throwable error) {
        log.error("[Handler] Error processing request: {}", error.getMessage(), error);
        
        if (error instanceof com.crediya.solicitudes.model.exception.InvalidLoanApplicationException ||
            error instanceof com.crediya.solicitudes.model.exception.InvalidLoanTypeException) {
            return ServerResponse.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(java.util.Map.of("error", "Bad Request", "message", error.getMessage()));
        }
        
        return ServerResponse.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(java.util.Map.of("error", "Internal Server Error", "message", "An unexpected error occurred"));
    }
}