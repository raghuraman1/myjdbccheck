package myjdbceg;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServlet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class Application {
	
	@Bean
	   public ServletRegistrationBean reg1() {
	      ServletRegistrationBean registration = new ServletRegistrationBean(new MyServlet(), "/services");
	     // registration.addUrlMappings("*.jws");
	      return registration;
	   }
	
	
	 @Bean 
	    public MultipartConfigElement multipartConfigElement() { 
	        MultipartConfigFactory factory = new MultipartConfigFactory(); 
	        factory.setMaxRequestSize("10MB");

	        factory.setMaxFileSize("10MB");

	        return factory.createMultipartConfig(); 
	    } 
	
	@Bean
	   public ServletRegistrationBean reg2() {
	      ServletRegistrationBean registration = new ServletRegistrationBean(new MyServlet1(), "/services1");
	      registration.setMultipartConfig(multipartConfigElement());
	      // registration.addUrlMappings("*.jws");
	      return registration;
	   }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
