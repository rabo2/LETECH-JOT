package kr.letech.study.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.letech.study.dto.AttachDTO;
import kr.letech.study.dto.CommonCode;
import kr.letech.study.dto.Navbar;
import kr.letech.study.dto.UserInfoVo;
import kr.letech.study.service.AccountService;
import kr.letech.study.service.AttachService;
import kr.letech.study.service.CommonCodeService;
import kr.letech.study.service.MenuService;

@Controller
public class CommonController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private AttachService attachService;

	@RequestMapping(value = { "/index", "" })
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
	public ResponseEntity<Map<String, String>> signUp(@RequestBody UserInfoVo userInfo) {

		ResponseEntity<Map<String, String>> entity = null;
		Map<String, String> dataMap = new HashMap<String, String>();

		try {
			accountService.save(userInfo);
			dataMap.put("Message", "OK");
			entity = new ResponseEntity<Map<String, String>>(dataMap, HttpStatus.OK);

		} catch (Exception e) {
			dataMap.put("Message", "FAIL");
			entity = new ResponseEntity<Map<String, String>>(dataMap, HttpStatus.BAD_REQUEST);

		}

		return entity;
	}

	@RequestMapping("/accessDeniedPage")
	public String accessDeniedPage() {
		return "common/accessDeniedPage";
	}

	@GetMapping("/navbar/{lvl}/{upCd}")
	@ResponseBody
	public List<Map<String, ?>> printNavbar(@PathVariable(value = "upCd") String upCd, @PathVariable("lvl") String lvl)
			throws Exception {

		if (upCd.equals("undefined")) {
			upCd = null;
		}

		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("upCd", upCd);
		paraMap.put("lvl", lvl);

		return menuService.getMenuList(paraMap);
	}

	@GetMapping("/download")
	@ResponseBody
	public ResponseEntity<Object> fileDownLoad(@RequestParam Map<String, String> paraMap) throws Exception {
		ResponseEntity<Object> entity = null;

		AttachDTO attach = attachService.getAttach(paraMap);

		try {
			Path path = Paths.get(attach.getUploadPath());
			Resource resource = new InputStreamResource(Files.newInputStream(path));

			File file = new File(attach.getUploadPath());

			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentDisposition(
					ContentDisposition.builder("attachment").filename(attach.getFileNm()).build());

			entity = new ResponseEntity<Object>(resource, httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			entity = new ResponseEntity<Object>(null, HttpStatus.CONFLICT);
		}
		return entity;
	}
}
