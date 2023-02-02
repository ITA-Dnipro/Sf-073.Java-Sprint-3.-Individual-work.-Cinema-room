package com.example.antifraud.controller.dto.transaction;


import com.example.antifraud.validation.Ipv4;
import com.example.antifraud.validation.ValidRegion;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.LuhnCheck;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
    @Positive(message = "Should be a positive number!")
    @NotNull(message = "Can't be null")
    private long amount;
    @Ipv4
    @NotEmpty
    private String ip;
    @Pattern(regexp="[\\d]{16}")
    @LuhnCheck
    @NotEmpty
    private String number;
    @ValidRegion
    private String region;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date;
}
