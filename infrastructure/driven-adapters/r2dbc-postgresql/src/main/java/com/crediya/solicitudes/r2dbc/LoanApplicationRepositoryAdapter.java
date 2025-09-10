package com.crediya.solicitudes.r2dbc;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;
import com.crediya.solicitudes.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.slf4j.Slf4j;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public Flux<LoanApplication> findByStatusIn(List<LoanStatus> statuses, int page, int size) {
        List<String> statusStrings = statuses.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        
        int offset = page * size;
        
        log.debug("[R2DBC] Finding applications by statuses={} page={} size={}", statusStrings, page, size);
        
        return repository.findByStatusInOrderByCreatedAtDesc(statusStrings, size, offset)
                .map(data -> mapper.map(data, LoanApplication.class))
                .map(app -> app.toBuilder()
                        .status(LoanStatus.valueOf(app.getStatus().name()))
                        .build());
    }

    @Override
    public Mono<Long> countByStatusIn(List<LoanStatus> statuses) {
        List<String> statusStrings = statuses.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        
        log.debug("[R2DBC] Counting applications by statuses={}", statusStrings);
        
        return repository.countByStatusIn(statusStrings);
    }
}