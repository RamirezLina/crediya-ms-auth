package co.com.crediya.api.dto;

import co.com.crediya.model.validations.UserValidations;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public record UserDto(

        @NotBlank(message = UserValidations.INVALID_NAME)
        String name,

        @NotBlank(message = UserValidations.INVALID_LAST_NAME)
        String lastName,

        @NotNull(message = UserValidations.INVALID_IDENTIFICATION)
        Long identification,

        @Past(message = UserValidations.INVALID_DATE)
        LocalDate birthDate,

        @NotBlank(message = UserValidations.INVALID_ADDRESS)
        String address,

        @Positive(message = UserValidations.INVALID_PHONE)
        long phone,

        @NotBlank(message = UserValidations.INVALID_NAME)
        @Email(message = UserValidations.INVALID_EMAIL_FORMAT)
        String email,

        @NotNull(message = UserValidations.INVALID_ROL)
        @Positive(message = UserValidations.ROL_NEGATIVE)
        Long rolId,

        @NotNull(message = UserValidations.INVALID_SALARY)
        @DecimalMin(value = "0", message = UserValidations.INVALID_BASE_SALARY)
        @DecimalMax(value = "15000000", message = UserValidations.INVALID_BASE_SALARY)
        double baseSalary
) implements IDto {
}
