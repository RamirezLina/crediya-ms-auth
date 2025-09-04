package co.com.crediya.model.error;

public class AuthException extends RuntimeException {

    public enum Type {
        TOKEN_NOT_FOUND("La peticion no incluye token"),
        CREDENTIALS_ERROR("Error al validar el token: %s"),
        NO_BEARER("Authenticacion invalida");
     
        private final String message;

        public AuthException build() {
            return new AuthException(this);
        }

        public AuthException build(String personalizedMessage) {
            String finalMessage = String.format(this.message, personalizedMessage);
            return new AuthException(finalMessage);
        }

        Type(String message) {
            this.message = message;
        }
    }

    private AuthException(AuthException.Type type) {
        super(type.message);
    }

    public AuthException(String personalizedMessage) {
        super(personalizedMessage);
    }
}