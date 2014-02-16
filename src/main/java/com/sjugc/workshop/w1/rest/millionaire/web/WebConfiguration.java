package com.sjugc.workshop.w1.rest.millionaire.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Configuration class for configuring Spring MVC.
 * 
 * @author mjovanovic
 */
@Configuration
@ComponentScan
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {

}
