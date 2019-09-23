package com.safb.practise.security;

import io.jsonwebtoken.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.www.*;

public class AuthorizationFilter extends BasicAuthenticationFilter
{
  public AuthorizationFilter(AuthenticationManager authManager)
  {
    super(authManager);
  }

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException
  {
    String header = req.getHeader(SecurityConstants.HEADER_STRING);

    if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX))
    {
      chain.doFilter(req, res);
      return;
    }

    UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
  {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (token != null)
    {
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

      String user = Jwts.parser()
              .setSigningKey(SecurityConstants.TOKEN_SECRET)
              .parseClaimsJws(token)
              .getBody()
              .getSubject();

      if (user != null)
      {
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
      }

      return null;
    }
    return null;
  }
}
