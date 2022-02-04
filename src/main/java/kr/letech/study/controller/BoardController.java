package kr.letech.study.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("pageInfo", page);
		
		return "board/main :: #boardList";
	}
	
	@GetMapping("/regist")
	public void registForm() {}

	@PostMapping("/regist")
	public void regist(@RequestBody Map<String, String> paraMap, Principal principal) throws Exception {
		paraMap.put("user", principal.getName());
		
		boardService.regist(paraMap);
	}
	
	@GetMapping("/detail")
	public String detail(@RequestParam Map<String, String> paraMap, Model model, Principal principal) throws Exception{
		
		paraMap.put("user", principal.getName());
		
		Map<String, ?> board = boardService.getBoard(paraMap);
		
		model.addAttribute("board",board);
		
		return "board/detail";
	}
	
	@GetMapping("/modify")
	public void modifyForm(@RequestParam Map<String, String>paraMap, Model model) throws Exception {
		Map<String, ?> board = boardService.getBoard(paraMap);
		model.addAttribute("board", board);
	}

	@PutMapping("/modify")
	public String modify(@RequestBody Map<String, String> paraMap) throws Exception {
		boardService.modifyBoard(paraMap);
		
		return "board/modify";
	}
	
	@DeleteMapping("/remove")
	public String remove(@RequestBody Map<String, String> paraMap) throws Exception{
		boardService.remove(paraMap);
		return "board/detail";
	}
}
