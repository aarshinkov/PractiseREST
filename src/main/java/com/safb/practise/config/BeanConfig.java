package com.safb.practise.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;

@Configuration
public class BeanConfig
{
  @Bean
  public PasswordEncoder passwordEncoder()
  {
    return new BCryptPasswordEncoder(12);
  }

  @Bean
  public SpringApplicationContext springApplicationContext()
  {
    return new SpringApplicationContext();
  }
}
