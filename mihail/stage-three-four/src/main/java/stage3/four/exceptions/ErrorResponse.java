package stage3.four.exceptions;

import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("error")
    private String error;

    public ErrorResponse(String error) {
        setError(error);
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
