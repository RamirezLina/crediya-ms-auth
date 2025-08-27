package co.com.crediya.usecase.exception;

import lombok.Getter;

public class BusinessException extends RuntimeException {

    public enum Type{
        EMAIL_ALREADY_EXISTS("Ya existe una cuenta asociada al correo electrónico o número de identificación");
        private final String message;

        public BusinessException build(){
            return new BusinessException(this);
        }

        Type(String message) {
            this.message = message;
        }
    }

    private final BusinessException.Type type;
    @Getter
    private String personalizedMessage;


    private BusinessException(BusinessException.Type type){
        super(type.message);
        this.type = type;
    }

    public BusinessException(BusinessException.Type type, String personalizedMessage){
        super(type.message);
        this.type = type;
        this.personalizedMessage = String.format(type.message, personalizedMessage);
    }

}
