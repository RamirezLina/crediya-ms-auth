package co.com.crediya.model.user;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {
    
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private long phone;
    private String email;
    private Rol rol;
    private double baseSalary;

    public void validate() {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo o vacío");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico no puede ser nulo o vacío");
        }
        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("El correo electrónico debe tener un formato válido");
        }
        if (baseSalary < 0 || baseSalary > 15000000) {
            throw new IllegalArgumentException("El salario base debe estar entre 0 y 15.000.000");
        }
    }
    
}
