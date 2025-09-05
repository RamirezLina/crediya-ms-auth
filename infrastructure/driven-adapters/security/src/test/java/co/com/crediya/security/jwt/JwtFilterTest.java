package co.com.crediya.security.jwt;

import co.com.crediya.model.error.AuthException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

class JwtFilterTest {

    private final JwtFilter filter = new JwtFilter();

    @Test
    void allowsLoginPath() {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/login").build());

        WebFilterChain chain = ex -> Mono.empty();
        StepVerifier.create(filter.filter(exchange, chain)).verifyComplete();
    }

    @Test
    void errorWhenNoAuthorizationHeader() {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/users").build());
        WebFilterChain chain = ex -> Mono.empty();

        StepVerifier.create(filter.filter(exchange, chain))
                .expectErrorSatisfies(t -> {
                    assertTrue(t instanceof AuthException);
                    assertEquals("La peticion no incluye token", t.getMessage());
                })
                .verify();
    }

    @Test
    void errorWhenHeaderIsNotBearer() {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Basic abc")
                        .build());
        WebFilterChain chain = ex -> Mono.empty();

        StepVerifier.create(filter.filter(exchange, chain))
                .expectErrorSatisfies(t -> {
                    assertTrue(t instanceof AuthException);
                    assertEquals("Authenticacion invalida", t.getMessage());
                })
                .verify();
    }

    @Test
    void passesAndStoresTokenWhenBearer() {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/users")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer abc.def")
                        .build());

        WebFilterChain chain = ex -> {
            assertEquals("abc.def", ex.getAttribute("token"));
            return Mono.empty();
        };

        StepVerifier.create(filter.filter(exchange, chain)).verifyComplete();
    }
}

