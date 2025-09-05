package co.com.crediya.r2dbc.entity;

public record UserWithRole(

        String email,
        String password,
        String rol,
        Boolean enabled,
        Boolean noexpired,
        Boolean nolocked,
        Boolean credentialnoexpired) {
}
