package com.example.antifraud.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    @Positive(message = "Should be a positive number!")
    @NotNull(message = "Can't be null")
    private long amount;
}
