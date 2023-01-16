package cinema.rest.dto;

import cinema.domain.model.TokenInfo;

public record TokenDTO(String token) {
    public TokenInfo toModel() {
        return new TokenInfo(token);
    }
}