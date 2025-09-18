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
    
    @Query("""
        SELECT lr.id, lr.user_id, lr.customer_document, lr.amount, lr.term as term_months,
               lr.email, lr.loan_type, lr.interest_rate, lr.monthly_payment, lr.status, lr.created_at,
               u.name as user_name, u.last_name as user_last_name, u.base_salary as user_base_salary
        FROM loan_requests lr
        LEFT JOIN dblink('host=crediya-db-autenticacion port=5432 dbname=crediya_autenticacion user=crediya_user password=crediya_pass',
                        'SELECT id::text, name, last_name, base_salary FROM users') 
                AS u(id text, name text, last_name text, base_salary numeric)
                ON lr.user_id = u.id
        WHERE lr.status IN (:statuses)
        ORDER BY lr.created_at DESC
        LIMIT :size OFFSET :offset
        """)
    Flux<LoanApplicationWithUserData> findByStatusInWithUserDataOrderByCreatedAtDesc(List<String> statuses, int size, int offset);
}