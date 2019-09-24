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

    log.debug("auth token: " + authentication.getName());

    SecurityContextHolder.getContext().setAuthentication(authentication);
    chain.doFilter(req, res);
  }

  private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws IOException
  {
    String token = request.getHeader(SecurityConstants.HEADER_STRING);

    if (token != null)
    {
      token = token.replace(SecurityConstants.TOKEN_PREFIX, "");

//      Jws<Claims> jwsClaims = Jwts.parser()
//              .setSigningKey(SecurityConstants.TOKEN_SECRET)
//              .parseClaimsJws(token);
      try
      {
        JwtParser jwtParser = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET);

        Jws claimsJws = jwtParser.parseClaimsJws(token);

        Claims claims = (Claims) claimsJws.getBody();

        Collection authorities
                = Arrays.stream(claims.get("rol").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        for (Object authority : authorities)
        {
          log.debug("authority: " + authorities.toString());
        }

//        String username = jwsClaims
//                .getBody()
//                .getSubject();
//
////        List<GrantedAuthority> authorities = ((List<?>) jwsClaims.getBody()
////                .get("rol")).stream()
////                .map(authority -> new SimpleGrantedAuthority((String) authority))
////                .collect(Collectors.toList());
////        HashMap<String, String> roles = (HashMap<String, String>) jwsClaims.getBody().get("rol");
//        log.debug("heree: " + jwsClaims.getBody().get("rol"));
//
//        ObjectMapper mapper = new ObjectMapper();
////        RoleModel rm = mapper.readValue(jwsClaims.getBody().get("rol").toString(), RoleModel.class);
//
//        List<GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwsClaims.getBody().get("rol"));
//
//        log.debug("authorities size: " + authorities.size());
//
//        for (GrantedAuthority authority : authorities)
//        {
//          log.debug("auth: " + authority.getAuthority());
//        }
//System.out.println(user.name); //John
//        if (!username.isEmpty())
//        {
//          return new UsernamePasswordAuthenticationToken(username, null, null);
//        }
//
//        log.debug("user: " + username);
//
//        List<GrantedAuthority> roles = new ArrayList<>();
//        roles.add(new SimpleGrantedAuthority("USER"));
        return new UsernamePasswordAuthenticationToken("a.arshinkov97@gmail.com", null, null);
      }
      catch (NullPointerException np)
      {
        return null;
      }
    }
    return null;
  }
}
