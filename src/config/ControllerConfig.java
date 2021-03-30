package config;
import org.springframework.context.annotation.*;
import controller.*;

@Configuration
public class ControllerConfig {
	@Bean
	public UserController helloController() {
		return new UserController();
	}
	@Bean
	public SqlController sqctr() {
		return new SqlController();
	}
}
