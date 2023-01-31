package antifraud.domain.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserIsActiveStatusResponse {

    private String status;

    public String setStatus(String userName, String status){
        return String.format("User %s %s", userName, status);
    }
}
