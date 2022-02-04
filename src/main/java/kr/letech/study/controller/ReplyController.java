package kr.letech.study.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RestController;

import kr.letech.study.service.ReplyService;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("/reply")
@Controller
@Slf4j
public class ReplyController<V> {
	
	@Autowired
	private ReplyService replyService;
	
	@GetMapping("/{boardNo}/{pageNum}")
	public String listReply(@PathVariable(value = "boardNo")String boardNo, @PathVariable(value =  "pageNum")int pageNum, @RequestParam(value = "target") String target, Model model, Principal principal) throws Exception {
		Map<String, Object> paraMap = new HashMap<>();
		paraMap.put("user", principal.getName());
		paraMap.put("pageNum", pageNum);
		paraMap.put("boardNo", boardNo);
		
		Map<String, Object> replyList = replyService.getReplyList(paraMap);
		
		model.addAllAttributes(replyList);
		model.addAttribute("boardNo", boardNo);
		
		return target;
	}
	
	
	@PostMapping("/regist")
	public String registReply(@RequestBody Map<String, String> paraMap, Principal principal, Model model) throws Exception{
		paraMap.put("id", principal.getName());
		
		replyService.registReply(paraMap);
		
		return paraMap.get("target");
	}
	
	@PutMapping("/modify")
	public String modifyReply(@RequestBody Map<String, String> paraMap, Model model) throws Exception{
		
		replyService.modifyReply(paraMap);
		
		return "board/detail :: #replyList";
	}
	
	@DeleteMapping("/remove")
	public String removeReply(@RequestBody Map<String, String> paraMap, Model model) throws Exception{
		
		replyService.removeReply(paraMap);
		
		return "board/detail :: #replyList";
		
	}
}
