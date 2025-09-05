package co.com.crediya.security.jwt;

import co.com.crediya.model.security.UserSecurity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class JwtProviderAdapterTest {

    private JwtProviderAdapter adapter;

    @BeforeEach
    void setUp() throws Exception {
        adapter = new JwtProviderAdapter();
        setField("secret", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"); // base64url of 32 zero bytes
        setField("expiration", 3600000); // 1 hour in ms
        setField("issuer", "test-issuer");
    }

    private void setField(String name, Object value) throws Exception {
        Field f = JwtProviderAdapter.class.getDeclaredField(name);
        f.setAccessible(true);
        f.set(adapter, value);
    }

    @Test
    void generateAndValidateToken_subjectMatches() {
        UserSecurity us = UserSecurity.builder()
                .email("user@mail.com")
                .rol("ADMIN, USER")
                .build();

        String token = adapter.generateToken(us);
        assertNotNull(token);
        assertTrue(adapter.validate(token));
        assertEquals("user@mail.com", adapter.getSubject(token));
    }
}

