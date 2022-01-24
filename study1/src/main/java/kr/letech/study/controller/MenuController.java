package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import kr.letech.study.service.MenuService;

@Controller
@RequestMapping("/navbar")
public class MenuController {

	@Autowired
	private MenuService menuService;

	@RequestMapping("")
	public String mainPage() {
		return "menu/main";
	}

	@GetMapping("/list")
	public String getList(Model model, @RequestParam Map<String, String> paraMap) throws Exception {

		List<Map<String, ?>> menuList = menuService.getMenuList(paraMap);

		model.addAttribute("menuList", menuList);

		return "menu/main :: #menuList";
	}

	@GetMapping("/detail")
	public String getMenu(Model model, @RequestParam Map<String, String> paraMap) throws Exception {

		Map<String, ?> menu = menuService.getMenu(paraMap);
		model.addAttribute("menu", menu);

		return "menu/main :: #detailCode";

	}

	@PostMapping("/regist")
	public String regist(Model model, @RequestParam Map<String, String> paraMap) throws Exception {
		menuService.registMenu(paraMap);

		List<Map<String, ?>> menuList = menuService.getMenuList(null);

		model.addAttribute("menuList", menuList);

		return "menu/main :: #menuList";
	}
	
	@PutMapping("/modify")
	public String modify(Model model, @RequestParam Map<String, String> paraMap) throws Exception {
		menuService.modifyMenu(paraMap);
		
		List<Map<String, ?>> menuList = menuService.getMenuList(null);
		
		model.addAttribute("menuList", menuList);
		
		return "menu/main :: #menuList";
	}
	
	@DeleteMapping("/remove")
	public String remove(Model model,@RequestParam Map<String, String> paraMap) throws Exception {
		menuService.removeMenu(paraMap);
		
		List<Map<String, ?>> menuList = menuService.getMenuList(null);
		
		model.addAttribute("menuList", menuList);
		
		return "menu/main :: #menuList";
	}
	

}
