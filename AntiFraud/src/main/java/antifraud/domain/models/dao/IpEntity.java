package antifraud.domain.models.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name="suspicious_ip_tbl")
public class IpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name= "ss_id")
    private Long id;
    @Column(name="ss_ip")
    private String ip;
}
