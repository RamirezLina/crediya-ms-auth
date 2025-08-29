package co.com.crediya.model.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DatabaseExceptionTest {

    @Test
    void testBuildFromType() {
        DatabaseException ex = DatabaseException.Type.ROL_NOT_EXISTS.build();
        assertEquals("El rol id ingresado no se asocia a ningun rol existente", ex.getMessage());
    }

    @Test
    void testBuildWithThrowable() {
        Throwable cause = new RuntimeException("Causa interna");
        DatabaseException ex = DatabaseException.Type.DATABASE_ERROR.build(cause);
        assertEquals("Error en la base de datos", ex.getMessage());
        assertEquals(cause, ex.getCause());
    }
}
