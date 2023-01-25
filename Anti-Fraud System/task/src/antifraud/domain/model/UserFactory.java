package antifraud.domain.model;

public class UserFactory {

    private UserFactory() {
    }

    public static User create(String name, String username, String password) {
        return new CustomUser(name, username, password);
    }
}