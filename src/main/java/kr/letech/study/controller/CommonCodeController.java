package kr.letech.study.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.letech.study.dto.CommonCode;
import kr.letech.study.service.CommonCodeService;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/commonCode")
@Slf4j
public class CommonCodeController {

	@Autowired
	private CommonCodeService cmnCdService;

	@RequestMapping("")
	public String commonCodePage() {
		return "commonCode/main";
	}

	@GetMapping("/{comnCd}")
	@ResponseBody
	public ResponseEntity<Object> commonCode(@PathVariable String comnCd) throws Exception {
		ResponseEntity<Object> entity = null;

		CommonCode commonCode = cmnCdService.getCommonCode(comnCd);
		entity = new ResponseEntity<Object>(commonCode, HttpStatus.OK);

		return entity;
	}

	@GetMapping("list/{comnCd}")
	@ResponseBody
	public ResponseEntity<Object> commonCodeList(@PathVariable(required = false) String comnCd) throws Exception {
		ResponseEntity<Object> entity = null;

		if (comnCd.equals("undefined")) {
			List<CommonCode> cmdList = cmnCdService.getCommonCodeList();
			entity = new ResponseEntity<Object>(cmdList, HttpStatus.OK);
		} else {
			CommonCode code = cmnCdService.getCommonCode(comnCd);
			entity = new ResponseEntity<Object>(code, HttpStatus.OK);
		}
		return entity;
	}

	@GetMapping("/upCode")
	public String commonCodeListByUpCd(Model model, @RequestParam Map<String, String> paraMap) throws Exception {
		model.addAttribute("codeList", cmnCdService.getCommonCodeListByUpCode(paraMap));

		return paraMap.get("target");
	}

	@PostMapping("/regist")
	@ResponseBody
	public ResponseEntity<Object> registCommonCode(@RequestBody CommonCode cmd) throws Exception {
		ResponseEntity<Object> entity = null;
		try {
			cmnCdService.registCommonCode(cmd);
			entity = new ResponseEntity<Object>(cmd, HttpStatus.OK);
		} catch (SQLException e) {
			entity = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@PutMapping("/modify")
	@ResponseBody
	public ResponseEntity<Object> modifyCommonCode(@RequestBody CommonCode cmd) throws Exception {
		ResponseEntity<Object> entity = null;

		try {
			cmnCdService.modifyCommandCode(cmd);
			entity = new ResponseEntity<Object>(cmd, HttpStatus.OK);
		} catch (SQLException e) {
			entity = new ResponseEntity<Object>(cmd, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@DeleteMapping("/remove/{comnCd}")
	public String remonveCommonCode(Model model, @PathVariable String comnCd) throws Exception {
		cmnCdService.removeCommonCode(comnCd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList", cmdList);

		return "commonCode/main :: #codeList";
	}
}
