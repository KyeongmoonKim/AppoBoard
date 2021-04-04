package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;

import org.springframework.stereotype.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import user.*;
import config.UserConfig;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class SqlController {
	@PostMapping("/sql/test")
	public List<Object> spProcessor(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8"); //중요
		
		HashMap<String,Object> map = new HashMap<String, Object>();
		String requestData = request.getReader().lines().collect(Collectors.joining());
		String[] ParamEqus = requestData.split("&");//배열 각 값이 "제이슨키값&제이슨밸류의 형태로 들어오나봄."
		int paraNum = ParamEqus.length;
		for(int i = 0; i < paraNum; i++) {
			String[] temp  = ParamEqus[i].split("=");
			System.out.println(temp[0] + " : " + temp[1]);
			map.put(temp[0], temp[1]);
		}
		int count = Integer.parseInt((String)map.get("counts"));
		
		/*System.out.println(map.get("sqlReq"));
		for(int i = 0; i < count; i++) {
			System.out.println(map.get("key"+Integer.toString(i)));
			System.out.println(map.get("value"+Integer.toString(i)));
			System.out.println(map.get("type"+Integer.toString(i)));
		}*/
		
		AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		SpDAO spdao = ctx.getBean("spDAO", SpDAO.class);
		Map<String, Object> result = spdao.exec(map);
		ctx.close();

		//test part
		/*UserVO temp = new UserVO();
		temp.setId("kkm8031");
		temp.setPwd("rudan93");
		temp.setName("kkm");
		ArrayList<UserVO> ret = new ArrayList<UserVO>();
		ret.add(temp);*/ 
		//json return하는 방법 필요함 지금 제대로 리턴이 안됌. json 처리 해서 응답하는 것 추가할 것
		//AbstractApplicationContext ctx = new AnnotationConfigApplicationContext(UserConfig.class);
		//UserDAO udao = ctx.getBean("userDao", UserDAO.class);

		return (List)result.get("MYCURSOR");	
		
	}
}
