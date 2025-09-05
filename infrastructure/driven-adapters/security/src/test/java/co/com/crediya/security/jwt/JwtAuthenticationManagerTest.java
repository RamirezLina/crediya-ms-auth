package co.com.crediya.security.jwt;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationManagerTest {

    @Mock
    private JwtProviderAdapter jwtProviderAdapter;

    @InjectMocks
    private JwtAuthenticationManager authenticationManager;

    @Test
    void authenticate_buildsAuthenticationWithAuthorities() {
        Claims claims = mock(Claims.class);
        when(claims.getSubject()).thenReturn("user@mail.com");
        when(claims.get("roles")).thenReturn(List.of(Map.of("authority", "ADMIN"), Map.of("authority", "USER")));
        when(jwtProviderAdapter.getClaims(anyString())).thenReturn(claims);

        Authentication input = new UsernamePasswordAuthenticationToken("ignored", "token123");

        StepVerifier.create(authenticationManager.authenticate(input))
                .expectNextMatches(auth ->
                        auth.getName().equals("user@mail.com") &&
                        auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN")) &&
                        auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"))
                )
                .verifyComplete();
    }

    @Test
    void authenticate_errorsOnJwtFailure() {
        when(jwtProviderAdapter.getClaims(anyString())).thenThrow(new RuntimeException("bad token"));
        Authentication input = new UsernamePasswordAuthenticationToken("ignored", "broken");

        StepVerifier.create(authenticationManager.authenticate(input))
                .expectErrorMatches(t -> t.getMessage().contains("bad token"))
                .verify();
    }
}

