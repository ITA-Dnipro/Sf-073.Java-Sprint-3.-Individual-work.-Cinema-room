package antifraud.domain.models.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class IpSuspiciousCreateRequest implements Serializable {

    private static final long serialVersionUID = 5203696206198469730L;
    private static final String zeroTo255 = "(\\d{1,2}|(0|1)\\" + "d{2}|2[0-4]\\d|25[0-5])";
    private static final String ipRegex = zeroTo255 + "\\."+ zeroTo255 + "\\." + zeroTo255 + "\\." + zeroTo255;
    @NotEmpty
    @Pattern(regexp = ipRegex, message = "ip for saving not valid, please provide valid ip address!")
    @JsonProperty(required = true)
    private String ip;
}
