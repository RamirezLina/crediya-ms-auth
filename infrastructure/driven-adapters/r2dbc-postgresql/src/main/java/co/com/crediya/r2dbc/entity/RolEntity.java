package co.com.crediya.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table(schema = "auth", value = "rol")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RolEntity {

    @Id
    @Column("id_rol")
    private Long id;
    @Column("nombre")
    private String name;
    @Column("descripcion")
    private String description;
}
