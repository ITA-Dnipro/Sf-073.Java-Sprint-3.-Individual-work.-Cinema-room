package antifraud.rest.dto;

import antifraud.domain.model.IP;
import antifraud.domain.model.IPFactory;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Builder
public record IpDTO(@JsonProperty(access = JsonProperty.Access.READ_ONLY)
                    Long id,
                    @NotEmpty
                    @Pattern(regexp = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$")
                    String ip) {

    public static IpDTO fromModel(IP savedIP) {
        return IpDTO.builder()
                .id(savedIP.getId())
                .ip(savedIP.getIpAddress())
                .build();
    }

    public IP toModel() {
        return IPFactory.create(ip);
    }
}
