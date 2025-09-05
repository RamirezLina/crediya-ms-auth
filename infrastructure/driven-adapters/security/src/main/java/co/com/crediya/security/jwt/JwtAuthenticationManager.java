package co.com.crediya.security.jwt;

import co.com.crediya.model.error.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Slf4j
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final JwtProviderAdapter jwtProviderAdapter;

    public JwtAuthenticationManager(JwtProviderAdapter jwtProviderAdapter) {
        this.jwtProviderAdapter = jwtProviderAdapter;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.just(authentication)
                .map(auth -> jwtProviderAdapter.getClaims(auth.getCredentials().toString()))
                .log()
                .doOnError( exception-> log.error("Error Message: {} \n Stack trace: {}", exception.getMessage(), exception.getStackTrace()))
                .onErrorResume(e -> Mono.error(AuthException.Type.CREDENTIALS_ERROR.build(e.getMessage())))
                .map(claims -> new UsernamePasswordAuthenticationToken(
                        claims.getSubject(),
                        null,
                        Stream.of(claims.get("roles"))
                                .map(role -> (List<Map<String, String>>) role)
                                .flatMap(role -> role.stream()
                                        .map(r -> r.get("authority"))
                                        .map(SimpleGrantedAuthority::new))
                                .toList())
                );
    }
}