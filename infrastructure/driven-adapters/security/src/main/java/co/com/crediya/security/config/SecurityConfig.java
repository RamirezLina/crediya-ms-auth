package co.com.crediya.security.config;

import co.com.crediya.security.jwt.JwtFilter;
import co.com.crediya.security.jwt.SecurityContextRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final Path path;
    private final SecurityContextRepository securityContextRepository;



    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, JwtFilter jwtFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange((authorize) -> authorize
                        .pathMatchers(path.getLogin()).permitAll()
                        .pathMatchers(path.getUsers()+"/**").hasAnyRole("ADMIN", "ASESOR")
                        .anyExchange().denyAll()
                )
                .addFilterAfter(jwtFilter, SecurityWebFiltersOrder.FIRST)
                .securityContextRepository(securityContextRepository)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .logout(ServerHttpSecurity.LogoutSpec::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
