package antifraud.model;

import lombok.*;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String username;

}
