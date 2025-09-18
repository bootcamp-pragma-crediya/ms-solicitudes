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
        log.info("[R2DBC] Saving loan application: userId={}, doc={}, name={}, salary={}", 
                app.getUserId(), app.getCustomerDocument(), app.getCustomerName(), app.getBaseSalary());
        
        LoanApplicationData data = LoanApplicationData.builder()
                .id(app.getId())
                .userId(app.getUserId())
                .customerDocument(app.getCustomerDocument())
                .email(app.getEmail())
                .customerName(app.getCustomerName())
                .amount(app.getAmount())
                .termMonths(app.getTermMonths())
                .loanType(app.getLoanType())
                .interestRate(app.getInterestRate())
                .baseSalary(app.getBaseSalary())
                .monthlyPayment(app.getMonthlyPayment())
                .status(app.getStatus().name())
                .build();
        
        log.info("[R2DBC] Data to save: name={}, salary={}", data.getCustomerName(), data.getBaseSalary());
        
        return repository.save(data)
                .doOnNext(saved -> log.info("[R2DBC] Saved data: name={}, salary={}", saved.getCustomerName(), saved.getBaseSalary()))
                .map(saved -> LoanApplication.builder()
                        .id(saved.getId())
                        .userId(saved.getUserId())
                        .customerDocument(saved.getCustomerDocument())
                        .email(saved.getEmail())
                        .customerName(saved.getCustomerName())
                        .amount(saved.getAmount())
                        .termMonths(saved.getTermMonths())
                        .loanType(saved.getLoanType())
                        .interestRate(saved.getInterestRate())
                        .baseSalary(saved.getBaseSalary())
                        .monthlyPayment(saved.getMonthlyPayment())
                        .status(LoanStatus.valueOf(saved.getStatus()))
                        .createdAt(saved.getCreatedAt() != null ? 
                            saved.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null)
                        .build());
    }

    @Override
    public Flux<LoanApplication> findByStatusIn(List<LoanStatus> statuses, int page, int size) {
        List<String> statusStrings = statuses.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
        
        int offset = page * size;
        
        log.info("[R2DBC-REAL] Finding applications by statuses={} page={} size={} offset={}", statusStrings, page, size, offset);
        
        return repository.findByStatusInWithUserDataOrderByCreatedAtDesc(statusStrings, size, offset)
                .doOnNext(data -> log.info("[R2DBC] Retrieved data: id={}, name='{}', salary={}, email={}", 
                    data.getId(), data.getUserName(), data.getUserBaseSalary(), data.getEmail()))
                .map(data -> LoanApplication.builder()
                        .id(data.getId())
                        .userId(data.getUserId())
                        .customerDocument(data.getCustomerDocument())
                        .email(data.getEmail())
                        .customerName(data.getUserName())
                        .amount(data.getAmount())
                        .termMonths(data.getTermMonths())
                        .loanType(data.getLoanType())
                        .interestRate(data.getInterestRate())
                        .baseSalary(data.getUserBaseSalary())
                        .monthlyPayment(data.getMonthlyPayment())
                        .status(LoanStatus.valueOf(data.getStatus()))
                        .createdAt(data.getCreatedAt() != null ? 
                            data.getCreatedAt().atOffset(java.time.ZoneOffset.UTC) : null)
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