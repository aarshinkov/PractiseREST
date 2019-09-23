package com.safb.practise.security;

import com.safb.practise.services.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.savedrequest.*;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter
{
  @Autowired
  private UserService userService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  protected void configure(HttpSecurity http) throws Exception
  {
    http.csrf().disable()
            .requestCache().requestCache(new NullRequestCache())
            .and()
            .authorizeRequests()
            .antMatchers("/v2/api-docs",
                    "/configuration/ui",
                    "/swagger-resources/**",
                    "/configuration/security",
                    "/swagger-ui.html",
                    "/webjars/**").permitAll()
            .antMatchers(HttpMethod.GET, "/api/users").permitAll()
            .antMatchers(HttpMethod.GET, "/api/users/**").permitAll()
            .antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL).permitAll()
            .antMatchers("/api/**").authenticated()
            .and()
            .addFilter(getAuthenticationFilter())
            .addFilter(new AuthorizationFilter(authenticationManager()))
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception
  {
    auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
  }

  public AuthenticationFilter getAuthenticationFilter() throws Exception
  {
    final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    filter.setFilterProcessesUrl("/login");
    return filter;
  }

}
