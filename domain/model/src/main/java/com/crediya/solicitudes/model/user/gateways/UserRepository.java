package com.crediya.solicitudes.model.user.gateways;

import com.crediya.solicitudes.model.user.User;
import reactor.core.publisher.Mono;

public interface UserRepository {
    Mono<User> findById(String userId);
}