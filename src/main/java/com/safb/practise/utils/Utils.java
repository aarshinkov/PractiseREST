package com.safb.practise.utils;

import java.security.*;
import java.util.*;
import org.springframework.stereotype.*;

@Component
public class Utils
{
  private static final Random RANDOM = new SecureRandom();
  private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

  public static String generateUserId(int length)
  {
    return generateRandomString(length);
  }

  private static String generateRandomString(int length)
  {
    StringBuilder builder = new StringBuilder(length);

    for (int i = 0; i < length; i++)
    {
      builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    }

    return new String(builder);
  }
}
