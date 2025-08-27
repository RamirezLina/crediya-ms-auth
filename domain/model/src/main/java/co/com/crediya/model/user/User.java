package co.com.crediya.model.user;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

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

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(UserValidations.INVALID_NAME);
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException(UserValidations.INVALID_LAST_NAME);
        }
        if (identification == null  || identification.equals(0L)) {
            throw new IllegalArgumentException(UserValidations.INVALID_IDENTIFICATION);
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(UserValidations.INVALID_EMAIL);
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException(UserValidations.INVALID_EMAIL_FORMAT);
        }
        if (baseSalary < 0 || baseSalary > 15000000) {
            throw new IllegalArgumentException(UserValidations.INVALID_BASE_SALARY);
        }
    }
}
