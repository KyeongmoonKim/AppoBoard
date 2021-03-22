package config;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.context.annotation.*;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry ) {
		registry.jsp("/WEB-INF/", ".jsp"); //WEB-INF에 .jsp 붙은 뷰화면을 결과로 보여주겠다.
	}
}
