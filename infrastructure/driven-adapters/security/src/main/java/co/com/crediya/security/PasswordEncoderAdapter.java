package co.com.crediya.security;

import co.com.crediya.model.security.gateways.PasswordEncoderGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
@Slf4j
public class PasswordEncoderAdapter implements PasswordEncoderGateway {

    private final PasswordEncoder passwordEncoder;

    @Override
    public String encodePassword(String originalPassword) {
        return passwordEncoder.encode(originalPassword);
    }

    @Override
    public boolean isPasswordCorrect(String dtoPassword, String validPassword) {
        return passwordEncoder.matches(dtoPassword, validPassword);
    }
}
