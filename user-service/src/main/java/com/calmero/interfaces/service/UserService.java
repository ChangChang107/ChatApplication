package com.calmero.interfaces.service;

import com.calmero.model.view.UserView;
import org.springframework.security.core.userdetails.UserDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<UserView> getUser();

    Flux<UserView> getUsers();

    Mono<UserDetails> loadUserDetails(String username);
}
