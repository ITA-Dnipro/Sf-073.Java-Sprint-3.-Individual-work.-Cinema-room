package stage3.four.models;

import com.google.gson.annotations.SerializedName;

public class Token {

    @SerializedName("token")
    private final String token;

    public Token(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
