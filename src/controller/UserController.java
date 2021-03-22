package controller;

import java.UserDAO;
import java.UserVO;
import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {
	@RequestMapping("/")
	public String loginMain() {
		return "login";
	}
	@PostMapping("/user/login")
	public String login() {
		return "";		
	}
}
