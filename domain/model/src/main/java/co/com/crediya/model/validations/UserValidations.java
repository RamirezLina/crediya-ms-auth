package co.com.crediya.model.validations;


public class UserValidations {
    public static final String INVALID_NAME = "El nombre no puede ser nulo o vacio";
    public static final String INVALID_LAST_NAME = "El apellido no puede ser nulo o vacio";
    public static final String INVALID_EMAIL = "El correo electronico no puede ser nulo o vacio";
    public static final String INVALID_IDENTIFICATION = "La identificacion no puede ser nula o vacia";
    public static final String INVALID_ADDRESS = "La direccion no puede ser nula o vacia";
    public static final String INVALID_EMAIL_FORMAT = "El correo electronico debe tener un formato válido";
    public static final String INVALID_BASE_SALARY = "El salario base debe estar entre 0 y 15.000.000";
    public static final String INVALID_DATE = "La fecha no es anterior al dia de hoy";
    public static final String INVALID_PHONE = "La número de telefono no puede ser negativo";
    public static final String INVALID_ROL = "El Rol no puede ser nulo";
    public static final String INVALID_SALARY = "El salario base no puede ser nulo";
    public static final String ROL_NEGATIVE = "El Rol no puede ser negativo";
    public static final String INVALID_PASSWORD = "La contraseña no puede ser nula o vacia";
    

    private UserValidations() {
    }
}

