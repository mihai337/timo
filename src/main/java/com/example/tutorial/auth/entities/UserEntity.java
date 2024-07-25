package com.example.tutorial.auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class UserEntity implements UserDetails {

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private int id;

    @Column(name = "username", nullable = false)
    @NotBlank(message = "Username should not be blank")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "Password should not be blank")
    @Size(min = 8, message = "Password should have at least 8 characters")
    private String password;

    @Column(name = "email", nullable = false)
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email should not be blank")
    private String email;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    private UserRole role;

    @OneToOne(mappedBy = "user")
    private RefreshToken refreshToken;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
//        return isAccountNonExpired;
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return isAccountNonLocked;
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return isCredentialsNonExpired;
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return isEnabled;
        return true;
    }
}
