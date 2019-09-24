package com.safb.practise.security;

import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import io.jsonwebtoken.*;
import java.io.*;
import java.util.*;
import java.util.stream.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.context.*;
import org.springframework.security.web.authentication.www.*;

public class AuthorizationFilter extends BasicAuthenticationFilter
{
  private Logger log = LoggerFactory.getLogger(getClass());

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

    log.debug("auth token: " + authentication);

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws IOException
  {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (token != null)
    {
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

      try
      {
        JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET);

        Jws claimsJws = jwtParser.parseClaimsJws(token);

        Claims claims = (Claims) claimsJws.getBody();

        String username = claims.getSubject();

        List<GrantedAuthority> authorities = getAuthoritiesFromJwsMap(claims, SecurityConstants.JWT_AUTH_KEY);

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
      }
      catch (NullPointerException np)
      {
        return null;
      }
    }
    return null;
  }

  private List<GrantedAuthority> getAuthoritiesFromJwsMap(Claims claims, String jwtAuthKey)
  {
    Map<String, Object> wrapperMap = (Map<String, Object>) claims;

    List<LinkedHashMap<String, String>> authPairs = (List<LinkedHashMap<String, String>>) wrapperMap.get(jwtAuthKey);

    List<GrantedAuthority> authorities = new ArrayList<>();

    for (LinkedHashMap<String, String> roles : authPairs)
    {
      authorities.add(new SimpleGrantedAuthority(roles.get("authority").toUpperCase()));
    }

    return authorities;
  }
}
