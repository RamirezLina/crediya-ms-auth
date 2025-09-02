package co.com.crediya.api.dto;

import co.com.crediya.model.validations.LoginValidations;
import co.com.crediya.model.validations.UserValidations;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(

        @NotBlank(message = LoginValidations.INVALID_EMAIL)
        @Email(message = LoginValidations.INVALID_EMAIL_FORMAT)
        String email,

        @NotBlank(message = LoginValidations.INVALID_PASSWORD)
        String password
) implements IDto{
}
