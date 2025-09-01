package com.crediya.solicitudes.r2dbc;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface LoanTypeR2dbcRepository extends Repository<LoanTypeData, String> {

    @Query("select count(1)>0 from loan_types where code = :code and active = true")
    Mono<Boolean> existsByCodeAndActiveTrue(String code);
}