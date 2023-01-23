package antifraud.domain.model;

public class UserFactory {

    private UserFactory() {
    }

    public static User create(String name, String userName, String password) {
        return new CustomUser(name, userName, password);
    }

    public static User createWithId(Long id, String name, String userName) {
        return new CustomUser(id, name, userName);
    }
}