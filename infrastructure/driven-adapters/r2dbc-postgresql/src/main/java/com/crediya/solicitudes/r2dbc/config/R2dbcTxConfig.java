package com.crediya.solicitudes.r2dbc.config;

import com.crediya.solicitudes.model.common.ReactiveTransaction;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.reactive.TransactionalOperator;

@Configuration
@ConditionalOnProperty(name = "app.mock-mode", havingValue = "false")
public class R2dbcTxConfig {
    @Bean ReactiveTransactionManager transactionManager(ConnectionFactory cf){ return new R2dbcTransactionManager(cf); }
    @Bean TransactionalOperator transactionalOperator(ReactiveTransactionManager tm){ return TransactionalOperator.create(tm); }
    @Bean ReactiveTransaction reactiveTransaction(TransactionalOperator op){ return op::transactional; }
    @Bean R2dbcEntityTemplate r2dbcEntityTemplate(ConnectionFactory cf){ return new R2dbcEntityTemplate(cf); }
}