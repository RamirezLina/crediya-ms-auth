package co.com.crediya.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderAdapterTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordEncoderAdapter adapter;

    @Test
    void encodePassword_delegatesToEncoder() {
        when(passwordEncoder.encode("plain")).thenReturn("encoded");
        assertEquals("encoded", adapter.encodePassword("plain"));
        verify(passwordEncoder).encode("plain");
    }

    @Test
    void isPasswordCorrect_usesMatches() {
        when(passwordEncoder.matches("raw", "hash")).thenReturn(true);
        assertTrue(adapter.isPasswordCorrect("raw", "hash"));
        verify(passwordEncoder).matches("raw", "hash");
    }
}

