package myjdbceg;

import javax.servlet.http.HttpServlet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
	@Bean
	public HttpServlet foo() {
	    return new MyServlet();
	}
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}

}
