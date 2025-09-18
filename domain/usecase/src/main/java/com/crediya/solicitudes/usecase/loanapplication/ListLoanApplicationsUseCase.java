package com.crediya.solicitudes.usecase.loanapplication;

import com.crediya.solicitudes.model.loanapplication.LoanApplication;
import com.crediya.solicitudes.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.solicitudes.model.loanstatus.LoanStatus;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
public class ListLoanApplicationsUseCase {
    
    private final LoanApplicationRepository repository;
    
    private static final List<LoanStatus> REVIEW_STATUSES = List.of(
        LoanStatus.PENDING_REVIEW,
        LoanStatus.REJECTED,
        LoanStatus.MANUAL_REVIEW
    );
    
    public Mono<PagedResult<LoanApplication>> execute(int page, int size) {
        return Mono.zip(
            repository.findByStatusIn(REVIEW_STATUSES, page, size)
                .flatMap(this::enrichWithUserData)
                .collectList(),
            repository.countByStatusIn(REVIEW_STATUSES)
        ).map(tuple -> {
            List<LoanApplication> applications = tuple.getT1();
            Long totalElements = tuple.getT2();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            
            return PagedResult.<LoanApplication>builder()
                .content(applications)
                .page(page)
                .size(size)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .build();
        });
    }
    
    private Mono<LoanApplication> enrichWithUserData(LoanApplication application) {
        return Mono.just(application);
    }
    
    @lombok.Data
    @lombok.Builder
    @lombok.NoArgsConstructor
    @lombok.AllArgsConstructor
    public static class PagedResult<T> {
        private List<T> content;
        private int page;
        private int size;
        private long totalElements;
        private int totalPages;
    }
}