package antifraud.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class CustomUser implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String userName;
    private String password;

    public CustomUser(String name, String userName, String password) {
        this.name = name;
        this.userName = userName;
        this.password = password;
    }

    public CustomUser(Long id, String name, String userName) {
        this.id = id;
        this.name = name;
        this.userName = userName;
    }

}