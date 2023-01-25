package antifraud.domain.model;

public interface User {
    Long getId();

    String getName();

    void setName(String name);

    String getUsername();

    void setUsername(String username);

    String getPassword();

    void setPassword(String password);
}