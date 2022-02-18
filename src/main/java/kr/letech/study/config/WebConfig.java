package kr.letech.study.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.elasticsearch.RestClientBuilderCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.navercorp.lucy.security.xss.servletfilter.XssEscapeServletFilter;

import kr.letech.study.interceptor.SecurityInterceptor;
import lombok.RequiredArgsConstructor;

@Configuration
public class WebConfig implements WebMvcConfigurer  {
	
	@Value("${custom.path.summernote}")
	private String summerNotePath;

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
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new SecurityInterceptor())
		.excludePathPatterns("/css/**", "/fonts/**", "/plugin/**", "/scripts/**");
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		this.summerNotePath = summerNotePath.replace("/", File.separator);
		registry.addResourceHandler("/summernoteImage/**").addResourceLocations("file:///"+summerNotePath+File.separator);
	}
}
