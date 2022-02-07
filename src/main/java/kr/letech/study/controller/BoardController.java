package kr.letech.study.controller;

import java.security.Principal;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("")
	public String mainPage(Model model) {
		model.addAttribute("boardDev", "CD018");
		return "board/main";
	}
	
	@GetMapping("/{boardDev}")
	public String printboardList(Model model, @PathVariable(value = "boardDev") String boardDev,@ModelAttribute("criteria") Criteria criteria) throws Exception{
		Map<String, Object> paraMap= new HashMap<>();
		
		paraMap.put("cri", criteria);
		paraMap.put("boardDev", boardDev);
		
		Map<String, Object> dataMap = boardService.getBoardList(paraMap);
		
		model.addAllAttributes(dataMap);
		
		return "board/main :: #boardList";
	}
	
	@GetMapping("/{boardDev}/{boardNo}")
	public String detail(@PathVariable Map<String, String> paraMap, Model model, Principal principal) throws Exception{
		
		paraMap.put("user", principal.getName());
		
		Map<String, ?> board = boardService.getBoard(paraMap);
		
		model.addAttribute("board",board);
		
		return "board/detail";
	}
	
	@GetMapping("/regist")
	public String registForm() {
		return "board/regist";
	}

	@PostMapping("")
	public String regist(@RequestBody Map<String, String> paraMap, Principal principal) throws Exception {
		paraMap.put("user", principal.getName());
		
		boardService.regist(paraMap);
		
		return "board/regist";
	}
	
	
	@GetMapping("/modify")
	public String modifyForm(@RequestParam Map<String, String>paraMap, Model model) throws Exception {
		Map<String, ?> board = boardService.getBoard(paraMap);
		model.addAttribute("board", board);
		model.addAttribute("boardDev", paraMap.get("boardDev"));
		
		return "board/modify";
	}

	@PutMapping("")
	public String modify(@RequestBody Map<String, String> paraMap) throws Exception {
		boardService.modifyBoard(paraMap);
		
		return "board/modify";
	}
	
	@DeleteMapping("")
	public String remove(@RequestBody Map<String, String> paraMap) throws Exception{
		boardService.remove(paraMap);
		return "board/detail";
	}
}
