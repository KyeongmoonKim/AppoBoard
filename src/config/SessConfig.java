package config;
import user.MyDate;

import org.springframework.context.annotation.*;

@Configuration
public class SessConfig {
	@Scope("session")
	@Bean
	public MyDate myDate() {
		return new MyDate();
	}
	
}
