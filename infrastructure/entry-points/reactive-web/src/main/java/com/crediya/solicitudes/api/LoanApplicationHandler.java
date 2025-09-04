package com.crediya.solicitudes.api;

import com.crediya.solicitudes.api.constants.ErrorMessages;
import com.crediya.solicitudes.api.constants.LogMessages;
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
                .doOnNext(req -> log.info(LogMessages.HANDLER_POST_REQUEST,
                        req.customerDocument(), req.loanType(), req.amount(), req.termMonths()))
                .flatMap(req -> {
                    var domain = mapper.toDomain(req);
                    return domain != null ? Mono.just(domain) : 
                           Mono.error(new com.crediya.solicitudes.model.exception.InvalidLoanApplicationException(
                               com.crediya.solicitudes.model.exception.ValidationMessage.BODY_REQUIRED.getMessage()));
                })
                .flatMap(useCase::execute)
                .map(mapper::toResponse)
                .doOnSuccess(resp -> log.info(LogMessages.HANDLER_CREATED, resp.id(), resp.status()))
                .flatMap(resp -> ServerResponse.created(URI.create("/api/v1/solicitud/" + resp.id()))
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
        
        return ServerResponse.status(500)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(java.util.Map.of("error", ErrorMessages.INTERNAL_SERVER_ERROR, "message", ErrorMessages.UNEXPECTED_ERROR));
    }
}