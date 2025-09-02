package co.com.crediya.model.user;
import co.com.crediya.model.validations.UserValidations;
import lombok.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private String id;
    private String name;
    private String lastName;
    private Long identification;
    private LocalDate birthDate;
    private String address;
    private long phone;
    private String email;
    private Long rolId;
    private double baseSalary;

    public Mono<User> validate() {
        if (name == null || name.trim().isEmpty()) {
            return Mono.error(new IllegalArgumentException(UserValidations.INVALID_NAME));
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            return Mono.error( new IllegalArgumentException(UserValidations.INVALID_LAST_NAME));
        }
        if (identification == null  || identification.equals(0L)) {
            return Mono.error( new IllegalArgumentException(UserValidations.INVALID_IDENTIFICATION));
        }
        if (email == null || email.trim().isEmpty()) {
            return Mono.error( new IllegalArgumentException(UserValidations.INVALID_EMAIL));
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return Mono.error( new IllegalArgumentException(UserValidations.INVALID_EMAIL_FORMAT));
        }
        if (baseSalary < 0 || baseSalary > 15000000) {
            return Mono.error( new IllegalArgumentException(UserValidations.INVALID_BASE_SALARY));
        }
        
        return Mono.just(this);
    }
}
