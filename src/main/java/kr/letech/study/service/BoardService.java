package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.BoardRepository;
import kr.letech.study.repository.ReplyRepository;
import kr.letech.study.vo.Account;
import kr.letech.study.vo.Criteria;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private ReplyRepository replyRepository;
	

	public List<Map<String, ?>> getBoardList(Criteria criteria) throws Exception {

		List<Map<String, ?>> boardList = boardRepository.selectBoardList(criteria);

		return boardList;
	}

	public void regist(Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("user"));

		paraMap.put("userNo", account.getUserNo());

		boardRepository.insertBoard(paraMap);
	}

	public Map<String, ?> getBoard(Map<String, String> paraMap) throws Exception {
		if (paraMap.get("from") == null) {
			boardRepository.updateViewCount(paraMap);
		}

		Map<String, Object> board = boardRepository.selectBoardByBoardNo(paraMap);
		if (board != null) {
			if (paraMap.get("user") != null && paraMap.get("user").equals(board.get("writer"))) {
				board.put("hasRole", "OK");
			}
			
			List<Map<String, ?>> replyList = replyRepository.selectReplyByBoardNo(paraMap);
			
			log.info(">>>>>>>{}", replyList);
			
			board.put("replyList", replyList);
		}

		return board;
	}

	public void modifyBoard(Map<String, String> paraMap) throws Exception {
		boardRepository.updateBoard(paraMap);
	}

	@Transactional
	public void remove(Map<String, String> paraMap) throws Exception {
		replyRepository.deleteAllReply(paraMap);
		boardRepository.deleteBoard(paraMap);
	}

}
