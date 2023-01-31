package antifraud.domain.models.dto;

import lombok.*;


import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter@Setter
public class UserDto implements Serializable {

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private static final long serialVersionUID = -2462354268805405694L;
    private long id;
    private String name;
    private String userName;
    private String password;

    private String role;
}
