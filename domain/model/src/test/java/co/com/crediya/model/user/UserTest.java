package co.com.crediya.model.user;

import co.com.crediya.model.validations.UserValidations;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

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
        StepVerifier.create(user.validate())
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void validate_shouldThrow_whenNameIsInvalid() {
        User user = User.builder().name("").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_NAME.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenLastNameIsInvalid() {
        User user = User.builder().name("Juan").lastName("").identification(123L).email("juan@mail.com").baseSalary(1000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_LAST_NAME.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenIdentificationIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(0L).email("juan@mail.com").baseSalary(1000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_IDENTIFICATION.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenEmailIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("").baseSalary(1000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_EMAIL.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenEmailFormatIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juanmail.com").baseSalary(1000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_EMAIL_FORMAT.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenBaseSalaryIsInvalid() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(-1).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_BASE_SALARY.equals(ex.getMessage()))
                .verify();
    }

    @Test
    void validate_shouldThrow_whenBaseSalaryIsTooHigh() {
        User user = User.builder().name("Juan").lastName("Perez").identification(123L).email("juan@mail.com").baseSalary(20000000).build();
        StepVerifier.create(user.validate())
                .expectErrorMatches(ex -> ex instanceof IllegalArgumentException &&
                        UserValidations.INVALID_BASE_SALARY.equals(ex.getMessage()))
                .verify();
    }
}
