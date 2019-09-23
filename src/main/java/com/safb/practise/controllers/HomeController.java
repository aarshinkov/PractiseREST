package com.safb.practise.controllers;

import org.slf4j.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController
{
  private final Logger log = LoggerFactory.getLogger(getClass());

  @GetMapping(value =
  {
    "", "/"
  })
  public String home()
  {
    return "redirect:/swagger-ui.html";
  }
}
