package roles;

import commands.ServerCommand;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AnonymousUser {


    protected static final HashSet<String> whitelist = new HashSet<>();
    protected ServerCommand command = ServerCommand.DEFAULT_COMMAND;

    public void execute(String command) {
        this.command = this.command.select(command);



        this.command.call();
    }



}
