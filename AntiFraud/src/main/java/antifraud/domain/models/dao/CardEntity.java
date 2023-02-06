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
@Table(name="cards_tbl")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="c_id")
    private Long id;
    @Column(name ="c_number",unique = true)
    private String number;
    @Column(name ="c_is_stolen")
    private boolean isStolen;
    @Column(name="c_max_allowed")
    private long maxAllowed;
    @Column(name="c_max_manual")
    private long maxManual;

}
