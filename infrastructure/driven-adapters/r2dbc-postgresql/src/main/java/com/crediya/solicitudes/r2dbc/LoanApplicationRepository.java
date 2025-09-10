package com.crediya.solicitudes.r2dbc;

import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface LoanApplicationRepository
        extends ReactiveCrudRepository<LoanApplicationData, String>,
        ReactiveQueryByExampleExecutor<LoanApplicationData> {

    @Query("SELECT * FROM loan_requests WHERE status IN (:statuses) ORDER BY created_at DESC LIMIT :size OFFSET :offset")
    Flux<LoanApplicationData> findByStatusInOrderByCreatedAtDesc(List<String> statuses, int size, int offset);

    @Query("SELECT COUNT(*) FROM loan_requests WHERE status IN (:statuses)")
    Mono<Long> countByStatusIn(List<String> statuses);
}