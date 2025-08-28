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
            return new BusinessException(finalMessage);
        }

        Type(String message) {
            this.message = message;
        }
    }


    private BusinessException(BusinessException.Type type){
        super(type.message);
    }

    public BusinessException(String personalizedMessage){
        super(personalizedMessage);
    }

}
