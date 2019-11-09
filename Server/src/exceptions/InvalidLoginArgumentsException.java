package exceptions;

public class InvalidLoginArgumentsException extends Server401Exception {
    public InvalidLoginArgumentsException() {
        super(ServerExceptionMessage.INVALID_LOGIN_ARGUMENTS);
    }
}
