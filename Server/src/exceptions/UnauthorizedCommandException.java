package exceptions;

public class UnauthorizedCommandException extends Server401Exception {
    public UnauthorizedCommandException() {
        super(ServerExceptionMessage.UNAUTHORIZED_COMMAND);
    }
}
