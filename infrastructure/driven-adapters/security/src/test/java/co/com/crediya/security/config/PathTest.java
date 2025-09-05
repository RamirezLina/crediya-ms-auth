package co.com.crediya.security.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {

    @Test
    void gettersAndSetters_work() {
        Path p = new Path();
        p.setUsers("/api/users");
        p.setLogin("/api/login");
        p.setExistUserByEmail("/api/users/exist");

        assertEquals("/api/users", p.getUsers());
        assertEquals("/api/login", p.getLogin());
        assertEquals("/api/users/exist", p.getExistUserByEmail());
    }
}

