package com.bolad.polls.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	
	//enable cross origin requests globally
	public void addCorsMappings(CorsRegistry registry){
		registry.addMapping("/**");
	}

}
