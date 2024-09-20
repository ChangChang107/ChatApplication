package com.calmero.controller;

import com.calmero.interfaces.controller.RegisterController;
import com.calmero.interfaces.repository.UserRepository;
import com.calmero.interfaces.service.RegisterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static com.calmero.constant.SuccessConstant.SUCCESS;

@ExtendWith(MockitoExtension.class)
class RegisterControllerImplTest {
    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RegisterService registerService;

    @InjectMocks
    private RegisterController registerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(registerController).build();
    }

    @Test
    void registerTest() {
        Mockito.when(registerController.register(null)).thenReturn(Mono.just(SUCCESS));
    }
}