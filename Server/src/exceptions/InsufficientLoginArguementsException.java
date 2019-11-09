package exceptions;

public class InsufficientLoginArguementsException extends Server401Exception {

    public InsufficientLoginArguementsException() {
        super(ServerExceptionMessage.INSUFFICIENT_LOGIN_ARGUMENTS);
    }


}
