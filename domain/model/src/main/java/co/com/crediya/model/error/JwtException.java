package co.com.crediya.model.error;

public class JwtException  extends RuntimeException {

    public enum Type {
        TOKEN_EXPIRED("El token ha expirado"),
        TOKEN_UNSUPPORTED("El token no es soportado"),
        TOKEN_MALFORMED("El token está mal formado"),
        BAD_SIGNATURE("La firma del token no es válida"),
        ILLEGAL_ARGUMENT("Argumento ilegal al procesar el token");

        private final String message;

        public JwtException build() {
            return new JwtException(this);
        }

        public JwtException build(String personalizedMessage) {
            String finalMessage = String.format(this.message, personalizedMessage);
            return new JwtException(finalMessage);
        }

        Type(String message) {
            this.message = message;
        }
    }

    private JwtException(JwtException.Type type) {
        super(type.message);
    }

    public JwtException(String personalizedMessage) {
        super(personalizedMessage);
    }
}