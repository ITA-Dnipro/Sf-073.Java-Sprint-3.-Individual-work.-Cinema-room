package com.example.antifraud.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Entity
@Getter
@Setter
@Table(name = "a_stolen_credit_card")
public class CreditCardEntity {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Column
    @Pattern(regexp="[\\d]{16}")
    @LuhnCheck
    @NotEmpty
    String number;
}
