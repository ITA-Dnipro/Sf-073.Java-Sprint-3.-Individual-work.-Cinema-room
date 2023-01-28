package antifraud.domain.model;

import antifraud.domain.model.enums.UserAccess;
import antifraud.domain.model.enums.UserRole;

public class UserFactory {

    private UserFactory() {
    }

    public static User create(String name, String username, String password) {
        return CustomUser.builder()
                .name(name)
                .username(username)
                .password(password)
                .build();
    }

    public static User createWithRole(String username, UserRole role) {
        return CustomUser.builder()
                .username(username)
                .role(role)
                .build();
    }

    public static User createWithAccess(String username, UserAccess access) {
        return CustomUser.builder()
                .username(username)
                .access(access)
                .build();
    }
}