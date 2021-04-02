package controller;

import user.*;
import config.UserConfig;
import config.SessConfig;

import java.text.SimpleDateFormat;
import java.util.Locale;

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
	public String login(Model model, HttpServletRequest request) {
		String id = request.getParameter("user_id");
		String pwd = request.getParameter("user_pw");
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		UserDAO udao = ctx.getBean("userDao", UserDAO.class);
		String ret = "";
		try {
			udao.login(id, pwd);
			long systemTime = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			// format에 맞게 출력하기 위한 문자열 변환
			String dTime = formatter.format(systemTime);
			model.addAttribute("todayDate", dTime);
			ret = "todayAppo";
		} catch(LoginFailException e) {
			ret = "loginX";
		}
		ctx.close();
		return ret;
	}
}
