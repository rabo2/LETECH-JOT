package kr.letech.study.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.letech.study.repository.ReplyRepository;
import kr.letech.study.vo.Criteria;
import kr.letech.study.vo.Page;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public Map<String, Object> getReplyList(Map<String, Object> paraMap) throws Exception{
		Map<String, Object> hashMap = new HashMap<>();
		
		
		Page page = new Page();
		
		int pageNum = Integer.parseInt(String.valueOf(paraMap.get("pageNum")));
		
		Criteria cri = new Criteria();
		cri.setPage(pageNum);
		page.setCri(cri);

		paraMap.put("pageStart", cri.getPageStart());
		
		List<Map<String, String>> replyList = replyRepository.selectReplyByBoardNo(paraMap);
		
		int cnt = 0;
		
		if(replyList.get(0) != null && replyList.get(0).size() > 0) {
			cnt = Integer.parseInt(String.valueOf(replyList.get(0).get("cnt")));
		}
		
		page.setTotalCnt(cnt);
		
		String user = (String) paraMap.get("user");
		for (Map<String, String> reply : replyList) {
			if(reply.get("writer").equals(user)) {
				reply.put("hasRole", "Y");
			}else {
				reply.put("hasRole", "N");
			}
		}
		
		hashMap.put("pageInfo", page);
		hashMap.put("replyList", replyList);
		
		return hashMap;
	}

	@Transactional
	public void registReply(Map<String, String> paraMap) throws Exception {
		replyRepository.insertReply(paraMap);
	}

	public void modifyReply(Map<String, String> paraMap) throws Exception {
		replyRepository.updateReply(paraMap);
	}

	public void removeReply(Map<String, String> paraMap) throws Exception {
		replyRepository.updateReply(paraMap);
	}
}
