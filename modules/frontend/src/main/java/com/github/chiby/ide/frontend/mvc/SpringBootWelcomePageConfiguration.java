package com.github.chiby.ide.frontend.mvc;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configure the welcome page 
 * 
 */
@Configuration
public class SpringBootWelcomePageConfiguration extends WebMvcConfigurerAdapter implements WebMvcConfigurer {

    /**
     * redirect a user to the welcome page when he visits tha app without a
     * destination url.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
    	registry.addViewController("/ide").setViewName("redirect:/ide/");
    	registry.addViewController("/ide/").setViewName("forward:/ide/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        super.addViewControllers(registry);
    }
}