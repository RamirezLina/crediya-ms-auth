package co.com.crediya.model.security;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserSecurityTest {

    @Test
    void builder_shouldCreateUserSecurity() {
        UserSecurity us = UserSecurity.builder()
                .email("user@mail.com")
                .password("secret")
                .rol("USER")
                .isEnabled(true)
                .accountNoExpired(true)
                .accountNoLocked(false)
                .credentialNoExpire(true)
                .build();

        assertEquals("user@mail.com", us.getEmail());
        assertEquals("secret", us.getPassword());
        assertEquals("USER", us.getRol());
        assertTrue(us.getIsEnabled());
        assertTrue(us.getAccountNoExpired());
        assertFalse(us.getAccountNoLocked());
        assertTrue(us.getCredentialNoExpire());
    }

    @Test
    void settersAndGetters_shouldWork() {
        UserSecurity us = new UserSecurity();
        us.setEmail("a@b.com");
        us.setPassword("p");
        us.setRol("ADMIN");
        us.setIsEnabled(false);
        us.setAccountNoExpired(false);
        us.setAccountNoLocked(true);
        us.setCredentialNoExpire(false);

        assertEquals("a@b.com", us.getEmail());
        assertEquals("p", us.getPassword());
        assertEquals("ADMIN", us.getRol());
        assertFalse(us.getIsEnabled());
        assertFalse(us.getAccountNoExpired());
        assertTrue(us.getAccountNoLocked());
        assertFalse(us.getCredentialNoExpire());
    }
}

