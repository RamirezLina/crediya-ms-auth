package co.com.crediya.model.user;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void validate_shouldPass_whenUserIsValid() {
        User user = User.builder()
                .name("Juan")
                .lastName("Perez")
                .identification(123L)
                .email("juan@mail.com")
                .baseSalary(1000)
                .build();
        assertDoesNotThrow(user::validate);
    }

    @Test
    void validate_shouldThrow_whenNameIsInvalid() {
        User user = User.builder().name("").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_NAME, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenLastNameIsInvalid() {
        User user = User.builder().name("Juan").lastName("").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_LAST_NAME, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenIdentificationIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(0L).email("juan@mail.com").baseSalary(1000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_IDENTIFICATION, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenEmailIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("").baseSalary(1000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_EMAIL, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenEmailFormatIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juanmail.com").baseSalary(1000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_EMAIL_FORMAT, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenBaseSalaryIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(-1).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_BASE_SALARY, ex.getMessage());
    }

    @Test
    void validate_shouldThrow_whenBaseSalaryIsTooHigh() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(20000000).build();
        Exception ex = assertThrows(IllegalArgumentException.class, user::validate);
        assertEquals(UserValidations.INVALID_BASE_SALARY, ex.getMessage());
    }
}

