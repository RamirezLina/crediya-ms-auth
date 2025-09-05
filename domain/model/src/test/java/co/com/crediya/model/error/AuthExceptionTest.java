package co.com.crediya.model.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthExceptionTest {

    @Test
    void build_shouldUseTypeMessage() {
        AuthException ex = AuthException.Type.TOKEN_NOT_FOUND.build();
        assertEquals("La peticion no incluye token", ex.getMessage());
    }

    @Test
    void buildWithParam_shouldFormatMessage() {
        AuthException ex = AuthException.Type.CREDENTIALS_ERROR.build("detalle");
        assertEquals("Error al validar el token: detalle", ex.getMessage());
    }

    @Test
    void buildNoBearer_shouldReturnMessage() {
        AuthException ex = AuthException.Type.NO_BEARER.build();
        assertEquals("Authenticacion invalida", ex.getMessage());
    }
}

