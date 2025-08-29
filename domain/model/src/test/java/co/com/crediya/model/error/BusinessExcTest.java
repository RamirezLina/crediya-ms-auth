package co.com.crediya.model.error;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BusinessExcTest {

    @Test
    void testBuildFromType()  {
        BusinessException ex = BusinessException.Type.EMAIL_ALREADY_EXISTS.build();
        assertEquals("Ya existe una cuenta asociada al correo electronico o numero de identificacion", ex.getMessage());
    }

    @Test
    void testBuildFromTypeWithPersonalizedMessage() {
        BusinessException ex = BusinessException.Type.EMAIL_ALREADY_EXISTS.build("Mensaje personalizado");
        assertEquals("Ya existe una cuenta asociada al correo electronico o numero de identificacion", ex.getMessage());
    }

}