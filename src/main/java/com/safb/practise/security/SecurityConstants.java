package com.safb.practise.security;

public class SecurityConstants
{
  public static final long EXPIRATION_TIME = 86000000; //10 days
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
  public static final String TOKEN_TYPE = "JWT";
  public static final String SIGN_UP_URL = "/api/users";
  public static final String TOKEN_SECRET = "odifjgergjioerjg";
  public static final String TOKEN_ISSUER = "secure-api";
  public static final String TOKEN_AUDIENCE = "secure-app";
  public static final String JWT_AUTH_KEY = "rol";
}
