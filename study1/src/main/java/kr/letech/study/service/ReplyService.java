package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.ReplyRepository;

@Service
public class ReplyService {
	
	@Autowired
	private ReplyRepository replyRepository;
	
	public List<Map<String,?>> getReplyList(Map<String, String> paraMap) throws Exception{
		return replyRepository.selectReplyByBoardNo(paraMap);
	}
	
	public void registReply(Map<String, String> paraMap) throws Exception{
		replyRepository.insertReply(paraMap);
	}
	
	public void modifyReply(Map<String, String> paraMap) throws Exception{
		replyRepository.updateReply(paraMap);
	}
	
	public void removeReply(Map<String, String> paraMap) throws Exception{
		replyRepository.deleteReply(paraMap);
	}
}
