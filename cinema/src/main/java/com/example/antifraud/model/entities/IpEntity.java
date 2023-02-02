package com.example.antifraud.model.entities;


import com.example.antifraud.validation.Ipv4;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "a_ip")
@NoArgsConstructor
public class IpEntity {
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;

    @Column
    @Ipv4
    @NotEmpty
    String ip;

}
