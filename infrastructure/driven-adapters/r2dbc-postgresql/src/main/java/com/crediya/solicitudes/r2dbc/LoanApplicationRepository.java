package com.crediya.solicitudes.r2dbc;

import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanApplicationRepository
        extends ReactiveCrudRepository<LoanApplicationData, String>,
        ReactiveQueryByExampleExecutor<LoanApplicationData> {
}