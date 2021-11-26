package com.vlpz.model;

import com.vlpz.model.enums.Role;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

@Data
@Entity
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String surname;
  private String password;
  @Column(unique = true)
  private String email;
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(
        new SimpleGrantedAuthority(role.name())
    );
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}
