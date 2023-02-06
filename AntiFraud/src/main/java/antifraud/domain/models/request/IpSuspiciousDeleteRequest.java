package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@Getter
public class IpSuspiciousDeleteRequest {

    private static final long serialVersionUID = -3644141393964467684L;
    private static final String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
    private static final String ipRegex = zeroTo255 + "\\."+ zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
    @NotEmpty
    @JsonProperty(required = true)
    @Pattern(regexp = ipRegex, message = "ip for deletion not valid, please provide valid ip address!")
    private String ipSuspicious;
    public IpSuspiciousDeleteRequest(String ipSuspiciousDeleteRequest) {
        this.ipSuspicious = ipSuspiciousDeleteRequest;
    }
}
