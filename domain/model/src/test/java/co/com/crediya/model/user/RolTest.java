package co.com.crediya.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RolTest {
    @Test
    void builder_shouldCreateRolCorrectly() {
        Rol rol = Rol.builder().id(1L).name("ADMIN").description("Administrador").build();
        assertEquals(1L, rol.getId());
        assertEquals("ADMIN", rol.getName());
        assertEquals("Administrador", rol.getDescription());
    }

    @Test
    void settersAndGetters_shouldWorkCorrectly() {
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setName("USER");
        rol.setDescription("Usuario");
        assertEquals(2L, rol.getId());
        assertEquals("USER", rol.getName());
        assertEquals("Usuario", rol.getDescription());
    }
}

