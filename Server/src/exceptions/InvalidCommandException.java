package exceptions;

public class InvalidCommandException extends Server401Exception {

    public InvalidCommandException() {
        super(ServerExceptionMessage.INVALID_COMMAND.MESSAGE);
    }

}
