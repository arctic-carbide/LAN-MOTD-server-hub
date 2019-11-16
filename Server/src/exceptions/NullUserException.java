package exceptions;

public class NullUserException extends Server401Exception {

    public NullUserException() {
        super(ServerExceptionMessage.NULL_USER);
    }

}
