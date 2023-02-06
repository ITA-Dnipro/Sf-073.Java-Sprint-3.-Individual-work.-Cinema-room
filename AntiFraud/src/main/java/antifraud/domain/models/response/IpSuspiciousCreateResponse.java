package antifraud.domain.models.response;

import antifraud.domain.models.dto.IpDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonPropertyOrder({"id", "ip"})
public class IpSuspiciousCreateResponse {

    private static final long serialVersionUID = -3630867413033951139L;
    private Long id;
    private String ip;

    public IpSuspiciousCreateResponse(IpDto ipDto) {
        this.id = ipDto.getId();
        this.ip = ipDto.getIp();
    }
}
