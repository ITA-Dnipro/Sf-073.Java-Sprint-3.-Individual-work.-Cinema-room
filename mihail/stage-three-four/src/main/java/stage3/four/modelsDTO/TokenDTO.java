package stage3.four.modelsDTO;

import com.google.gson.annotations.SerializedName;

public class TokenDTO {

    @SerializedName("token")
    private final String token;

    public TokenDTO(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
