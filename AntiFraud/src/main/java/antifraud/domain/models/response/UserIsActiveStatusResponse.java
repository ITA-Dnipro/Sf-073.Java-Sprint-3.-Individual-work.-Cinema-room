package antifraud.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserIsActiveStatusResponse implements Serializable {

    private static final long serialVersionUID = 4156511907444923575L;
    private String status;

    public String setStatus(String userName, String status){
        return String.format("User %s %s", userName, status);
    }
}
