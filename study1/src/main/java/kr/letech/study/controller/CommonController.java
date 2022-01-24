package kr.letech.study.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.letech.study.service.AccountService;
import kr.letech.study.vo.UserInfoVo;

@Controller
@RequestMapping("/")
public class CommonController {
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping("/index")
	public String indexPage() {
		return "common/index";
	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "common/login";
	}
	
	@GetMapping("/signupPage")
	public String signupPage() {
		return "common/signup";
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public ResponseEntity<Map<String, String>> signUp(@RequestBody UserInfoVo userInfo){
		
		ResponseEntity<Map<String,String>> entity = null;
		Map<String,String> dataMap = new HashMap<String,String>();
		
		try {
			accountService.save(userInfo);
			dataMap.put("Message", "OK");
			entity = new ResponseEntity<Map<String,String>> (dataMap,HttpStatus.OK);
			
		} catch (Exception e) {
			dataMap.put("Message", "FAIL");
			entity = new ResponseEntity<Map<String,String>> (dataMap,HttpStatus.BAD_REQUEST);
			
		}

		return entity;
	}
	
	@RequestMapping("/accessDeniedPage")
	public String accessDeniedPage() {
		return "common/accessDeniedPage";
	}

}
