package co.com.crediya.model.security.gateways;

public interface PasswordEncoderGateway {
    
    String encodePassword(String originalPassword);

    boolean isPasswordCorrect(String dtoPassword, String validPassword);
    
}
