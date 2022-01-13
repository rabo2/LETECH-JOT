package kr.letech.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CommonController {
	
	@RequestMapping("/")
	public String indexPage() {
		return "indexPage";
	}
	
	@RequestMapping("login")
	public String loginPage() {
		return "common/login";
	}
	
	@RequestMapping("main")
	public String mainPage() {
		return "common/mainPage";
	}
	
}
