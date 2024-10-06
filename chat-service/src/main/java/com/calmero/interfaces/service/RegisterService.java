package com.calmero.interfaces.service;

import com.calmero.model.request.RegisterRequest;

public interface RegisterService {
    <R> R register(RegisterRequest registerRequest);
}
