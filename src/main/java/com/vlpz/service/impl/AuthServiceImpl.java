package com.vlpz.service.impl;

import com.vlpz.dto.UserDto;
import com.vlpz.model.User;
import com.vlpz.model.enums.Role;
import com.vlpz.repository.UserRepository;
import com.vlpz.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.vlpz.service.impl.UserServiceImpl.mapUserDtoToUser;
import static com.vlpz.service.impl.UserServiceImpl.mapUserToUserDto;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  @Value("${spring.auth.admin-domain}")
  private String ADMIN_DOMAIN;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Unable to find user email!"));
  }

  @Override
  public UserDto signIn(UserDto userDto) {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            userDto.getEmail(),
            userDto.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);

    User user = (User) authentication.getPrincipal();
    return mapUserToUserDto(user);
  }

  @Override
  public UserDto signUp(UserDto userDto) {
    User user = mapUserDtoToUser(userDto);
    log.info("createUser: about to register a new user with email {}", user.getEmail());

    user.setRole(userDto.getEmail().contains(ADMIN_DOMAIN) ? Role.ADMIN : Role.STUDENT);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    user = userRepository.save(user);
    log.info("User with id {} successfully registered", user.getId());

    return signIn(userDto);
  }

  @Override
  public void signOut() {
    log.info("signing out");
    SecurityContextHolder.clearContext();
  }

}
