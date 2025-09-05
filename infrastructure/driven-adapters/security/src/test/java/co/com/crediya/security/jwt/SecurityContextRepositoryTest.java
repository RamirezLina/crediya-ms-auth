package co.com.crediya.security.jwt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SecurityContextRepositoryTest {

    @Mock
    private JwtAuthenticationManager jwtAuthenticationManager;

    @InjectMocks
    private SecurityContextRepository repository;

    @Test
    void load_buildsSecurityContextFromToken() {
        MockServerWebExchange exchange = MockServerWebExchange.from(
                MockServerHttpRequest.get("/api/users").build());
        exchange.getAttributes().put("token", "jwt-token");

        Authentication auth = new UsernamePasswordAuthenticationToken(
                "user@mail.com", null, List.of(new SimpleGrantedAuthority("ADMIN")));

        when(jwtAuthenticationManager.authenticate(any())).thenReturn(Mono.just(auth));

        StepVerifier.create(repository.load(exchange))
                .expectNextMatches(sc -> sc.getAuthentication().getName().equals("user@mail.com") &&
                        sc.getAuthentication().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")))
                .verifyComplete();
    }
}

