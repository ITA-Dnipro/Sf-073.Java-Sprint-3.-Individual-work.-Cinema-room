package antifraud.domain.models.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class IpSuspiciousDeleteResponse {

    String status;

    @JsonIgnore
    String ipAddress;

    public IpSuspiciousDeleteResponse(String ipSuspiciousAddress) {
        this.ipAddress = ipSuspiciousAddress;
        setStatus(ipSuspiciousAddress);
    }

    private void setStatus(String ipSuspiciousAddress) {
        this.status = "IP " + ipSuspiciousAddress + " successfully removed!";
    }
}
