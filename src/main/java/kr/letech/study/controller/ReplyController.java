package kr.letech.study.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.letech.study.service.ReplyService;

@RequestMapping("/reply")
@RestController
public class ReplyController {
	
	@Autowired
	private ReplyService replyService;
	
	@PostMapping("/regist")
	public String registReply(@RequestBody Map<String, String> paraMap, Model model, Principal principal) throws Exception{
		paraMap.put("id", principal.getName());
		
		replyService.registReply(paraMap);
		
		model.addAttribute(replyService.getReplyList(paraMap));
		
		return paraMap.get("target");
	}
	
	@PutMapping("/modify")
	public String modifyReply(@RequestBody Map<String, String> paraMap, Model model) throws Exception{
		
		replyService.modifyReply(paraMap);
		
		model.addAttribute("replyList", replyService.getReplyList(paraMap));
		return "board/detail :: #replyList";
	}
	
	@DeleteMapping("/remove")
	public String removeReply(@RequestBody Map<String, String> paraMap, Model model) throws Exception{
		
		model.addAttribute("replyList", replyService.getReplyList(paraMap));
		replyService.removeReply(paraMap);
		
		return "board/detail :: #replyList";
		
	}
}
