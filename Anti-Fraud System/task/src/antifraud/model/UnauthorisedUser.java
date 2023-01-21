package antifraud.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_table")
public class UnauthorisedUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NonNull
    Long id;
    @NonNull
    String name;
    @NonNull
    String username;
    @NonNull
    String password;
    String role;
}
