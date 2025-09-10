package com.crediya.solicitudes.model.loanapplication.gateways;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoanApplicationRepository {
    Mono<LoanApplication> save(LoanApplication application);
    Flux<LoanApplication> findByStatusIn(List<LoanStatus> statuses, int page, int size);
    Mono<Long> countByStatusIn(List<LoanStatus> statuses);
}
