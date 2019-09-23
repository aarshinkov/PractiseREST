package com.safb.practise.config;

import org.springframework.beans.*;
import org.springframework.context.*;

public class SpringApplicationContext implements ApplicationContextAware
{
  private static ApplicationContext context;

  public ApplicationContext getApplicationContext()
  {
    return context;
  }

  @Override
  public void setApplicationContext(ApplicationContext context) throws BeansException
  {
    this.context = context;
  }

  public static Object getBean(String beanName)
  {
    return context.getBean(beanName);
  }
}
