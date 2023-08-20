package com.storagesensei.config;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Configuration
public class StorageSenseiConfig implements WebMvcConfigurer {

  @Override
  public void addViewControllers(final ViewControllerRegistry registry) {
    //WebMvcConfigurer.super.addViewControllers(registry);
    registry.addViewController("/home").setViewName("index");
    registry.addViewController("/").setViewName("index");
    registry.addViewController("/login").setViewName("login");
  }

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    //WebMvcConfigurer.super.addResourceHandlers(registry);

    registry.addResourceHandler("/jquery/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/jquery/3.6.4/");

    registry.addResourceHandler("/bootstrap/**")
        .addResourceLocations("classpath:/META-INF/resources/webjars/bootstrap/5.3.0/");
  }

  @Bean
  public TemplateEngine thymeleafTemplateResolver() {
    TemplateEngine templateEngine = new TemplateEngine();
    templateEngine.addDialect(new LayoutDialect());

    return templateEngine;
  }

  @Bean
  public LayoutDialect layoutDialect() {
    return new LayoutDialect();
  }

  /*@Bean
  public SpringTemplateEngine templateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    //templateEngine.setTemplateResolver(thymeleafTemplateResolver());
    templateEngine.addDialect(layoutDialect());

    return templateEngine;
  }*/
}
