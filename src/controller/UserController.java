package controller;

import user.*;
import config.UserConfig;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	@GetMapping("/")
	public String loginMain() {
		return "login";
	}
	@GetMapping("/sql")
	public String sqlMain() {
		return "test";
	}
	@PostMapping("/user/login")
	public String login(HttpServletRequest request) {
		String id = request.getParameter("user_id");
		String pwd = request.getParameter("user_pw");
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		UserDAO udao = ctx.getBean("userDao", UserDAO.class);
		String ret = "";
		try {
			udao.login(id, pwd);
			ret = "loginO";
		} catch(LoginFailException e) {
			ret = "loginX";
		}
		ctx.close();
		return ret;
	}
}
