package antifraud.model;



public class DeleteResponse {
    public DeleteResponse(){

    }
    public DeleteResponse(String username) {
        this.username = username;
        this.status = "Deleted successfully!";
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    String username;
    String status;
}
