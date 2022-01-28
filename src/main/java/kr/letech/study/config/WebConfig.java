package kr.letech.study.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

@Configuration
public class WebConfig {
	
	/*
	 * xssFilter
	*/
	@Bean
	public FilterRegistrationBean xssEscapeServletFilter() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(new XssEscapeServletFilter());
	    registrationBean.setOrder(1);
	    registrationBean.addUrlPatterns("/*");
	    return registrationBean;
	}
}
