package com.safb.practise.config;

import com.safb.practise.security.*;
import org.springframework.web.servlet.support.*;

public class RestSpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{

  @Override
  protected Class<?>[] getRootConfigClasses()
  {
    return null;
  }

  @Override
  protected Class<?>[] getServletConfigClasses()
  {
    return new Class[]
    {
      RestAppConfig.class
    };
  }

  @Override
  protected String[] getServletMappings()
  {
    return new String[]
    {
      "/",
      "/*",
      "/**"
    };
  }
}
