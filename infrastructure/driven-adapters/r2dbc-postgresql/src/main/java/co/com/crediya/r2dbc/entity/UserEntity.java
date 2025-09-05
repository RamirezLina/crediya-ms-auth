package co.com.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Table(schema = "auth", value = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserEntity {

    @Id
    @Column("id_usuario")
    private Long id;

    @Column("nombre")
    private String name;

    @Column("apellido")
    private String lastName;

    @Column("documento_identidad")
    private long identification;

    @Column("fecha_nacimiento")
    private LocalDate birthDate;

    @Column("direccion")
    private String address;

    @Column("telefono")
    private long phone;

    @Column("email")
    private String email;
    
    @Column("id_rol")
    private Long rolId;
    
    @Column("salario_base")
    private double baseSalary;

    @Column("password")
    private String password;
    
    @Column("is_enabled")
    private Boolean isEnabled;

    @Column("account_no_expired")
    private Boolean accountNoExpired;

    @Column("account_no_locked")
    private Boolean accountNoLocked;

    @Column("credential_no_expired")
    private Boolean credentialNoExpired;
    
}
