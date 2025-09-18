package com.crediya.solicitudes.api.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SecurityHeadersConfigTest {

    @Mock
    private ServerWebExchange exchange;
    
    @Mock
    private WebFilterChain chain;
    
    @Mock
    private ServerHttpResponse response;

    @Test
    void shouldAddSecurityHeaders() {
        SecurityHeadersConfig filter = new SecurityHeadersConfig();
        HttpHeaders headers = new HttpHeaders();
        
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        Mono<Void> result = filter.filter(exchange, chain);

        StepVerifier.create(result)
                .verifyComplete();

        verify(chain).filter(exchange);
        
        // Verify security headers were set
        assert headers.containsKey("Content-Security-Policy");
        assert headers.containsKey("Strict-Transport-Security");
        assert headers.containsKey("X-Content-Type-Options");
        assert headers.containsKey("Server");
        assert headers.containsKey("Cache-Control");
        assert headers.containsKey("Pragma");
        assert headers.containsKey("Referrer-Policy");
    }

    @Test
    void shouldSetCorrectHeaderValues() {
        SecurityHeadersConfig filter = new SecurityHeadersConfig();
        HttpHeaders headers = new HttpHeaders();
        
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        filter.filter(exchange, chain).block();

        assert "default-src 'self'; frame-ancestors 'self'; form-action 'self'".equals(headers.getFirst("Content-Security-Policy"));
        assert "max-age=31536000;".equals(headers.getFirst("Strict-Transport-Security"));
        assert "nosniff".equals(headers.getFirst("X-Content-Type-Options"));
        assert "".equals(headers.getFirst("Server"));
        assert "no-store".equals(headers.getFirst("Cache-Control"));
        assert "no-cache".equals(headers.getFirst("Pragma"));
        assert "strict-origin-when-cross-origin".equals(headers.getFirst("Referrer-Policy"));
    }

    @Test
    void shouldCallNextFilter() {
        SecurityHeadersConfig filter = new SecurityHeadersConfig();
        HttpHeaders headers = new HttpHeaders();
        
        when(exchange.getResponse()).thenReturn(response);
        when(response.getHeaders()).thenReturn(headers);
        when(chain.filter(exchange)).thenReturn(Mono.empty());

        filter.filter(exchange, chain).block();

        verify(chain, times(1)).filter(exchange);
    }
}