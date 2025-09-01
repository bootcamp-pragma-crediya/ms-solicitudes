package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
@Slf4j
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "false")
public class LoanApplicationRepositoryAdapter
        extends ReactiveAdapterOperations<LoanApplication, LoanApplicationData, String, LoanApplicationRepository>
        implements com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository {

    public LoanApplicationRepositoryAdapter(LoanApplicationRepository repository, ObjectMapper mapper) {
        super(repository, mapper, data ->
                mapper.map(data, LoanApplication.class));
    }

    @Override
    public Mono<LoanApplication> save(LoanApplication app) {
        log.debug("[R2DBC] Saving loan application for doc={} type={}", app.getCustomerDocument(), app.getLoanType());
        return super.save(app).map(saved ->
                saved.toBuilder()
                        .status(LoanStatus.valueOf(saved.getStatus().name()))
                        .build());
    }
}