package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SqlController {
	@PostMapping("/sql/test")
	public String spProcessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8"); //중요
		HashMap<String,String> map = new HashMap<String, String>();
		String requestData = request.getReader().lines().collect(Collectors.joining());
		String[] ParamEqus = requestData.split("&");//배열 각 값이 "제이슨키값&제이슨밸류의 형태로 들어오나봄."
		int paraNum = ParamEqus.length;
		for(int i = 0; i < paraNum; i++) {
			String[] temp  = ParamEqus[i].split("=");
			map.put(temp[0], temp[1]);
		}
		int count = Integer.parseInt(map.get("counts"));
		System.out.println(map.get("sqlReq"));
		for(int i = 0; i < count; i++) {
			System.out.println(map.get("key"+Integer.toString(i)));
			System.out.println(map.get("value"+Integer.toString(i)));
			System.out.println(map.get("type"+Integer.toString(i)));
		}
		//json return하는 방법 필요함 지금 제대로 리턴이 안됌. json 처리 해서 응답하는 것 추가할 것
		return "test";
	}
}
