package kr.letech.study.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.letech.study.dto.CommonCode;
import kr.letech.study.service.CommonCodeService;
import kr.letech.study.utility.Constant;

@Controller
@RequestMapping("/navbars")
public class MenuController {

	@Autowired
	private CommonCodeService cmnCdService;
	
	@GetMapping("/{upCd}")
	@ResponseBody
	public ResponseEntity<Object> navList(@PathVariable Map<String, String> paraMap) throws Exception{
		ResponseEntity<Object> entity = null;
		
		String mncd = Constant.MENU_CD;
		try {
			List<CommonCode> navbarList = cmnCdService.getCommonCodeListByUpCode(paraMap);
			entity = new ResponseEntity<Object>(navbarList, HttpStatus.OK);
		}catch (SQLException e) {
			entity = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	
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
