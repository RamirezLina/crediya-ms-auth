package co.com.crediya.model.security;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserSecurity {

    private String email;
    private String password;
    private String rol;
    private Boolean isEnabled;
    private Boolean accountNoExpired;
    private Boolean accountNoLocked;
    private Boolean credentialNoExpire;
    
}
