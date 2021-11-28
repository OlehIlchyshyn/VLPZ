package com.vlpz.config;

import com.vlpz.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration()
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired @Lazy
  private AuthService authService;

  @Bean(BeanIds.AUTHENTICATION_MANAGER)
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
      throws Exception {
    authenticationManagerBuilder
        .userDetailsService(authService)
        .passwordEncoder(passwordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
          .antMatchers("/api/auth/*").permitAll()
          .antMatchers("/api/account/*").hasAnyRole("STUDENT", "ADMIN")
          .antMatchers(HttpMethod.POST, "/api/task").hasRole("ADMIN")
          .antMatchers(HttpMethod.PUT, "api/task/*").hasRole("ADMIN")
          .antMatchers(HttpMethod.DELETE, "api/task/*").hasRole("ADMIN")
          .antMatchers(HttpMethod.GET, "/api/task").hasAnyRole("STUDENT", "ADMIN")
          .antMatchers(HttpMethod.GET, "api/task/*").hasAnyRole("STUDENT", "ADMIN")
          .antMatchers(HttpMethod.POST, "api/task/*").hasAnyRole("STUDENT", "ADMIN")
          .antMatchers("/api/admin/*").hasRole("ADMIN")
          .anyRequest().authenticated()
          .and()
        .cors()
          .disable()
        .csrf()
          .disable()
        .httpBasic();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}