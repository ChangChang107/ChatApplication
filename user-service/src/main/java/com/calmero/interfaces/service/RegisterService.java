package com.calmero.interfaces.service;

import com.calmero.model.request.RegisterRequest;
import reactor.core.publisher.Mono;

public interface RegisterService {
    Mono<String> register(Mono<RegisterRequest> registerRequest);
}
