package com.safb.practise.config;

import org.springframework.context.annotation.*;
import springfox.documentation.builders.*;
import static springfox.documentation.builders.PathSelectors.regex;
import springfox.documentation.service.*;
import springfox.documentation.spi.*;
import springfox.documentation.spring.web.plugins.*;
import springfox.documentation.swagger2.annotations.*;

@EnableSwagger2
@Configuration
public class SwaggerConfig
{
  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(regex("/api.*"))
            .build();
  }

  // Describe your apis
  private ApiInfo apiInfo()
  {
    return new ApiInfoBuilder()
            .title("Practising REST API")
            .description("REST API for practising creation and configuring Spring REST API")
            .version("1.0.0")
            .build();
  }
}
