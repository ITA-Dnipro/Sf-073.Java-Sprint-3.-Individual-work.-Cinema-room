package com.example.antifraud.model.entities;


import com.example.antifraud.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "a_user")
@NoArgsConstructor
public class UserEntity implements UserDetails {
    @JsonIgnore
    @Id
    @GeneratedValue
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    Long id;
    @NotEmpty
    String name;
    @NotEmpty
    @Column(unique = true)
    String username;
    @NotEmpty
    @JsonProperty(access =  JsonProperty.Access.WRITE_ONLY)
    String password;

    @Enumerated(EnumType.STRING)
    Role role;

    @JsonIgnore
    boolean locked;

    public UserEntity(String name, String username, String password, Role role, boolean locked) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.locked = locked;
    }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(role);
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}
