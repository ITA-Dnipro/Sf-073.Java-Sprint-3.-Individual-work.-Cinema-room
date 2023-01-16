package cinema.domain.model;

import lombok.Getter;

@Getter
public class TokenInfo {
    private String token;

    public TokenInfo(String token) {
        this.token = token;
    }

}