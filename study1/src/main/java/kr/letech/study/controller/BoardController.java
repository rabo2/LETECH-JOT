package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.letech.study.service.BoardService;
import kr.letech.study.vo.Criteria;
import kr.letech.study.vo.Page;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String mainPage() {
		return "board/main";
	}
	
	@GetMapping("/list")
	public String printboardList(Model model, @ModelAttribute("criteria") Criteria criteria) throws Exception{
		
		List<Map<String, ?>> boardList = boardService.getBoardList(criteria);
		
		Page page = new Page();
		page.setCri(criteria);
		
		page.setTotalCnt(Integer.parseInt(String.valueOf(boardList.get(0).get("cnt"))));
		
		log.info(">>>>>>>>>>>>>>{}",page);
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageInfo", page);
		
		return "board/main :: #boardList";
	}
	
}
