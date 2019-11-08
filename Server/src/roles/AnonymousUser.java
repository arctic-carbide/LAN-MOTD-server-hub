package roles;

import java.util.HashSet;

public class AnonymousUser extends UserProfile {


    protected static final HashSet<String> whitelist = new HashSet<>();

    public void call(String command) {
        this.command = this.command.select(command);



        this.command.call();
    }



}
