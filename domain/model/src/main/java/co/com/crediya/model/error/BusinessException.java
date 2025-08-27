package co.com.crediya.model.error;

public class BusinessException extends RuntimeException {

    public enum Type{
        EMAIL_ALREADY_EXISTS("Ya existe una cuenta asociada al correo electronico o numero de identificacion");
        private final String message;

        public BusinessException build(){
            return new BusinessException(this);
        }

        public BusinessException build(String personalizedMessage){
            String finalMessage = String.format(this.message, personalizedMessage);
            return new BusinessException(this, finalMessage);
        }

        Type(String message) {
            this.message = message;
        }
    }

    private final BusinessException.Type type;

    private BusinessException(BusinessException.Type type){
        super(type.message);
        this.type = type;
    }

    public BusinessException(BusinessException.Type type, String personalizedMessage){
        super(personalizedMessage);
        this.type = type;
    }

}
