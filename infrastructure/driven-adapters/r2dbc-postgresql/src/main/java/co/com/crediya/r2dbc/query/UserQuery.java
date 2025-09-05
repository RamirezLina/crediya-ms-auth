package co.com.crediya.r2dbc.query;

public class UserQuery {

    public static final String USER_WITH_ROLE = """
     SELECT
                     u.id_usuario AS id,
                     u.email AS email,
                     u.password AS password,
                     u.id_rol AS rolId,
                     r.nombre AS rol,
                     u.is_enabled AS enabled,
                     u.account_no_expired AS noexpired,
                     u.account_no_locked AS nolocked,
                     u.credential_no_expired AS credentialnoexpired
                 FROM auth.usuario u
                 JOIN auth.rol r ON r.id_rol = u.id_rol
                 WHERE u.email = :email
""";

    private UserQuery() {
    }
}
