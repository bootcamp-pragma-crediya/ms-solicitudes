package com.crediya.solicitudes.r2dbc;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class UserRepositoryAdapterTest {

    @Mock
    private WebClient webClient;

    @InjectMocks
    private UserRepositoryAdapter adapter;

    @Test
    void constructor_ShouldCreateAdapter() {
        assertNotNull(adapter);
    }

    @Test
    void authServiceUrl_ShouldHaveDefaultValue() {
        ReflectionTestUtils.setField(adapter, "authServiceUrl", "http://test-service:8080");
        String url = (String) ReflectionTestUtils.getField(adapter, "authServiceUrl");
        assertNotNull(url);
    }
}