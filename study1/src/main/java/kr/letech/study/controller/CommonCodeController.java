package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.letech.study.service.CommonCodeService;
import kr.letech.study.vo.CommonCode;

@Controller
@RequestMapping("/commonCode")
public class CommonCodeController {
	
	@Autowired
	private CommonCodeService cmnCdService;

	@RequestMapping("")
	public String commonCodePage() {
		return "commonCode/main";
	}

	@PostMapping("list")
	public String commonCodeList(Model model) throws Exception {
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList",cmdList);

		return "commonCode/main :: #codeList";
	}
	@GetMapping("{comnCd}")
	public String detailCode(Model model, @PathVariable String comnCd) throws Exception{
		CommonCode code = cmnCdService.getCommonCode(comnCd);
		
		model.addAttribute("code", code);
		
		return "commonCode/main :: #detailCode";
	}
	
	@PostMapping("/regist")
	public String registCommonCode(Model model, CommonCode cmd) throws Exception {
		cmnCdService.registCommonCode(cmd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList",cmdList);

		return "commonCode/main :: #codeList";
	}

	@PostMapping("/modify")
	public String modifyCommonCode(Model model, CommonCode cmd) throws Exception {
		cmnCdService.modifyCommandCode(cmd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList",cmdList);

		return "commonCode/main :: #codeList";
	}

	@GetMapping("/remove/{comnCd}")
	public String remonveCommonCode(Model model, @PathVariable String comnCd) throws Exception {
		cmnCdService.removeCommonCode(comnCd);
		List<CommonCode> cmdList = cmnCdService.getCommonCodeList();

		model.addAttribute("cmdList",cmdList);

		return "commonCode/main :: #codeList";
	}
}
