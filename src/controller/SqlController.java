package controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SqlController {
	@PostMapping("/sql/test")
	public String login(HttpServletRequest request) {
		return "loginO";
	}
}
