package com.example.antifraud.model.entities;


import com.example.antifraud.model.enums.Region;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Table(name = "a_transaction")
@NoArgsConstructor
public class TransactionEntity {
    @Id
    @GeneratedValue
    Long id;
    private long amount;
    @NotEmpty
    private String ip;
    @NotEmpty
    private String number;
    @Enumerated(EnumType.STRING)
    private Region region;

    private LocalDateTime date;
}
