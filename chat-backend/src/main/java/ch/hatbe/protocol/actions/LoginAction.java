package ch.hatbe.protocol.actions;

public class LoginAction extends Action {
    @Override
    public boolean handle() {

        System.out.println("HANDLE THE LOGIN ACTION");
        return true;
    }
}
