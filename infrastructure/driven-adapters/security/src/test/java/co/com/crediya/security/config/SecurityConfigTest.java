package co.com.crediya.security.config;

import co.com.crediya.security.jwt.JwtFilter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.junit.jupiter.api.Assertions.*;

class SecurityConfigTest {

    @Test
    void passwordEncoder_isBCrypt() {
        SecurityConfig config = new SecurityConfig(new Path(), Mockito.mock(co.com.crediya.security.jwt.SecurityContextRepository.class));
        PasswordEncoder pe = config.passwordEncoder();
        assertNotNull(pe);
        assertInstanceOf(BCryptPasswordEncoder.class, pe);
    }
}
