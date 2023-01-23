package antifraud.domain.model;

public class UserFactory {

    private UserFactory() {
    }

    public static User create(String name, String userName, String password) {
        return new CustomUser(name, userName, password);
    }

}