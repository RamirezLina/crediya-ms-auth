package co.com.crediya.security.jwt;

import co.com.crediya.model.error.AuthException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
public class JwtFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        if (path.contains("login") || path.contains("/v3/api-docs") || path.contains("/doc/")) {
            return chain.filter(exchange);
        }
        
        String auth = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null) {
            return Mono.error(AuthException.Type.TOKEN_NOT_FOUND.build());
        }
        
        if (!auth.startsWith("Bearer ")) {
            return Mono.error(AuthException.Type.NO_BEARER.build());
        }
        
        String token = auth.replace("Bearer ", "");
        exchange.getAttributes().put("token", token);
        return chain.filter(exchange);
    }
}
