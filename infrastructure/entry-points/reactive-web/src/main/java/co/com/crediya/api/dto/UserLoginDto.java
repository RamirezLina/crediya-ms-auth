package co.com.crediya.api.dto;

import co.com.crediya.model.validations.UserValidations;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(

        @NotBlank(message = UserValidations.INVALID_EMAIL)
        @Email(message = UserValidations.INVALID_EMAIL_FORMAT)
        String email,

        @NotBlank(message = UserValidations.INVALID_PASSWORD)
        String password
) implements IDto {
}
