package com.akash.moviebookingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.akash.moviebookingapp.config.JwtFilter;

import jakarta.servlet.DispatcherType;

@SpringBootApplication
public class MovieBookingAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieBookingAppApplication.class, args);
	}
	
	@Bean
	public FilterRegistrationBean<JwtFilter> jwtFilter(){
		FilterRegistrationBean<JwtFilter> fb = new FilterRegistrationBean<>();
		fb.setFilter(new JwtFilter());	
		fb.addUrlPatterns("/api/v1/*");
		return fb;
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}
	
}
