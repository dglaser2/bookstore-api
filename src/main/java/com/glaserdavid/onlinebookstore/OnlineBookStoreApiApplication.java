package com.glaserdavid.onlinebookstore;

import com.glaserdavid.onlinebookstore.filters.AuthFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineBookStoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineBookStoreApiApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean<AuthFilter> filterFilterRegistrationBean() {
		FilterRegistrationBean<AuthFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(new AuthFilter());
		filterRegistrationBean.addUrlPatterns("/api/books/*");
		filterRegistrationBean.addUrlPatterns("/api/orders/*");
		filterRegistrationBean.addUrlPatterns("/api/reviews/*");
		return filterRegistrationBean;
	}
}
