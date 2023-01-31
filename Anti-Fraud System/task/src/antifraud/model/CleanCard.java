package antifraud.model;

import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clean_cards")
public class CleanCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @NotEmpty
    String number;
    @Column(name = "MAX_ALLOWED")
    Integer maxAllowed;
    @Column(name = "MAX_MANUAL")
    Integer maxManual;
}
