package com.safb.practise.config;

import org.springframework.context.annotation.*;
import org.thymeleaf.spring5.*;
import org.thymeleaf.spring5.templateresolver.*;
import org.thymeleaf.spring5.view.*;

@Configuration
public class ViewConfig
{
  @Bean
  public SpringResourceTemplateResolver srtr()
  {
    SpringResourceTemplateResolver srtr = new SpringResourceTemplateResolver();
    srtr.setPrefix("/WEB-INF/pages/");
    srtr.setSuffix(".html");
    srtr.setTemplateMode("HTML");
    srtr.setCharacterEncoding("UTF-8");
    srtr.setCacheable(false);
    srtr.setOrder(1);

    return srtr;
  }

  @Bean
  public SpringTemplateEngine templateEngine()
  {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(srtr());

    return templateEngine;
  }

  @Bean
  public ThymeleafViewResolver viewResolver()
  {
    ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    viewResolver.setCharacterEncoding("UTF-8");

    return viewResolver;
  }
}
