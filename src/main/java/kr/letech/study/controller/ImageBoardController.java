package kr.letech.study.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/imageBoards")
public class ImageBoardController {

	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("boardDev", "CD019");
		return "board/main";
	}
}
