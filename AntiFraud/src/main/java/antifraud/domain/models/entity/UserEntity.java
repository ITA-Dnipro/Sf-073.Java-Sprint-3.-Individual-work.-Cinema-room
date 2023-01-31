package antifraud.domain.models.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
@Entity
@Table(name = "users_tbl")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "u_id")
    private Long id;
    @Column(name = "u_name", nullable = false)
    private String name;
    @Column(name = "u_username", nullable = false, unique = true)
    private String userName;
    @Column(name = "u_password", nullable = false)
    private String password;
    @Column(name="u_is_active")
    private boolean isActive;
    @Column(name="u_roles")
    private String roles;

}
