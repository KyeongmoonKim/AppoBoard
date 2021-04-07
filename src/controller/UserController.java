package controller;

import user.*;
import config.UserConfig;
import config.SessConfig;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	@GetMapping("/user/Main")
	public String sqlMain(Model model, @RequestParam(value = "date", required = false) String date) throws UnsupportedEncodingException {
		model.addAttribute("todayDate", date);
		return "todayAppo";
	}
	@GetMapping("/user/Main2")
	public String sqlMain2(HttpServletRequest request, @RequestParam(value = "Date", required = false) String date) throws UnsupportedEncodingException {
		if(date.length()>10) date = date.substring(0, 10);
		request.setAttribute("Date", date);
		return "monthAppo";
	}
	@PostMapping("/user/login")
	public String login(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8"); 
		HttpSession session = request.getSession();
		String id = request.getParameter("user_id");
		String pwd = request.getParameter("user_pw");
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		UserDAO udao = ctx.getBean("userDao", UserDAO.class);
		session.setAttribute("userId", id);
		session.setAttribute("isLogin", "true");
		String ret = "";
		try {
			udao.login(id, pwd);
			long systemTime = System.currentTimeMillis();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
			// format에 맞게 출력하기 위한 문자열 변환
			String dTime = formatter.format(systemTime);
			session.setAttribute("currDate", dTime);
			model.addAttribute("todayDate", dTime);
			ret = "todayAppo";
		} catch(LoginFailException e) {
			ret = "loginX";
		}
		ctx.close();
		return ret;
	}
	@GetMapping("/user/makeAppo")
	public String makeAppo() {
		return "makeAppo";
	}
	@PostMapping("/user/insertAppo")
	public String insertAppo(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		long systemTime = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
		// format에 맞게 출력하기 위한 문자열 변환
		String dTime = formatter.format(systemTime);
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		
		String title = (String)request.getParameter("title");
		String explanation = (String)request.getParameter("explanation"); //없으면 길이 0임
		String startDate = (String)request.getParameter("startDate");
		String endDate = (String)request.getParameter("endDate");
		String userId = (String)session.getAttribute("userId");
		AppointmentDAO Adao = ctx.getBean("appoDao", AppointmentDAO.class);
		AppointmentVO Avo = new AppointmentVO();
		Avo.setTitle(title);
		Avo.setStartDate(startDate);
		Avo.setEndDate(endDate);
		Avo.setExplanation(explanation);
		Avo.setUserId(userId);
		Adao.makeAppo(Avo);

		model.addAttribute("todayDate", dTime);
		ctx.close();
		return "todayAppo";
	}
}
