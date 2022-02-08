package kr.letech.study.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.letech.study.dto.Criteria;
import kr.letech.study.service.BoardService;

@Controller
@RequestMapping("/imageBoards")
public class ImageBoardController {

	@Autowired
	private BoardService boardService;
	
	
	@RequestMapping("")
	public String main(Model model) {
		model.addAttribute("boardDev", "CD019");
		return "imageBoard/main";
	}
	
	@GetMapping("/{boardDev}")
	public String getImageBoardList(@PathVariable(value = "boardDev")String boardDev, @ModelAttribute("criteria") Criteria criteria, Model model) throws Exception {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("boardDev", boardDev);
		paraMap.put("cri", criteria);
		
		model.addAllAttributes(boardService.getBoardList(paraMap));
		
		return "imageBoard/main :: #boardList";
	}

	@GetMapping("/{boardDev}/{boardNo}")
	public String getIamgeBoard(@PathVariable Map<String, String> paraMap, Model model, Principal principal) throws Exception {
		
		paraMap.put("user", principal.getName());
		
		Map<String, ?> board = boardService.getBoard(paraMap);
		
		model.addAttribute("board",board);
		
		return "imageBoard/detail";
	}
	
	
	@PostMapping("")
	public String registImageBoard(MultipartHttpServletRequest multiRequest) throws Exception {
		List<MultipartFile> files = multiRequest.getFiles("files");
		
		Map<String,String> paraMap = new HashMap<>();
		Principal principal = multiRequest.getUserPrincipal();

		paraMap.put("title", multiRequest.getParameter("title"));
		paraMap.put("content", multiRequest.getParameter("content"));
		paraMap.put("writer", principal.getName());
		paraMap.put("boardDev", multiRequest.getParameter("boardDev"));
		paraMap.put("boardClass", multiRequest.getParameter("boardClass"));
		
		boardService.registImageFile(files, paraMap);
		
		return "/imageBoards";
	}
	
}
