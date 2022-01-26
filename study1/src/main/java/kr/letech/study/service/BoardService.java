package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.BoardRepository;
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
	
	public List<Map<String, ?>> getBoardList(Criteria criteria) throws Exception {

		List<Map<String, ?>> boardList = boardRepository.selectBoardList(criteria);

		return boardList;
	}

	public void regist(Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("user"));
		
		paraMap.put("userNo", account.getUserNo());
		
		boardRepository.insertBoard(paraMap);
	}

	public Map<String, String> selectBoardByBoardNo(Map<String, String> paraMap) throws Exception {
		Map<String, String> board = boardRepository.selectBoardByBoardNo(paraMap);
		
		if(paraMap.get("user").equals(board.get("writer"))){
			board.put("hasRole", "OK");
		};
		
		return board;
	}
}
