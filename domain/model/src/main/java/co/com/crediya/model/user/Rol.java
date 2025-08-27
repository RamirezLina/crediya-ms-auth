package co.com.crediya.model.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Rol {

    private Long id;
    private String name;
    private String description;
    
}
