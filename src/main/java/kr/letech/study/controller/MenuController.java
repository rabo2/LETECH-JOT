package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import kr.letech.study.dto.CommonCode;
import kr.letech.study.service.CommonCodeService;
import kr.letech.study.service.MenuService;

@Controller
@RequestMapping("/navbars")
public class MenuController {

	@Autowired
	private CommonCodeService cmnCdService;

	@RequestMapping("")
	public String mainPage() {
		return "menu/main";
	}
	
	@GetMapping("/{lvl}/{comnCd}")
	@ResponseBody
	public List<CommonCode> navList(@PathVariable("lvl")int lvl, @PathVariable("comnCd") String comnCd) throws Exception {
		CommonCode cd = new CommonCode();
		cd.setLvl(lvl);
		cd.setUpCd(comnCd);
		
		List<CommonCode> commonCodeList = cmnCdService.getNavbarList(cd);
		return commonCodeList;
	}
}
