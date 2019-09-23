package com.safb.practise.security;

import com.fasterxml.jackson.databind.*;
import com.safb.practise.config.*;
import com.safb.practise.dto.*;
import com.safb.practise.requests.*;
import com.safb.practise.services.*;
import io.jsonwebtoken.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.web.authentication.*;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
  private Logger log = LoggerFactory.getLogger(getClass());

  private final AuthenticationManager authenticationManager;

  public AuthenticationFilter(AuthenticationManager authenticationManager)
  {
    this.authenticationManager = authenticationManager;
  }

  @Override
  public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException
  {
    try
    {
      UserLoginRequest creds = new ObjectMapper()
              .readValue(req.getInputStream(), UserLoginRequest.class);

      List<GrantedAuthority> roles = new ArrayList<>();
//      roles.add(new SimpleGrantedAuthority("USER"));

      return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), roles));
    }
    catch (IOException e)
    {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException, ServletException
  {
    String username = ((User) auth.getPrincipal()).getUsername();

    log.debug("username: " + username);
    String token = Jwts.builder()
            .setSubject(username)
            .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
            .compact();

    UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

    UserDto userDto = userService.getUserByEmail(username);

    res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
    res.addHeader("Public ID", userDto.getPublicId());
  }

}
