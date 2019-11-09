package exceptions;

import exceptions.Server401Exception;

public class UnauthorizedCommandException extends Server401Exception {
    public UnauthorizedCommandException() {
        super(ServerExceptionMessage.UNAUTHORIZED);
    }
}
