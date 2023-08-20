package com.storagesensei.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

  /*@GetMapping("/")
  public ModelAndView index() {
    return new ModelAndView("index.html");
  }*/

  //@GetMapping("/index")
  @RequestMapping("/index")
  public String index() {
    return "index";
  }
}
