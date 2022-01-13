package kr.letech.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	
	@GetMapping("login")
	public String loginPage() {
		return "common/login";
	}
}
