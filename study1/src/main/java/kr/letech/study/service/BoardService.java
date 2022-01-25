package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.BoardRepository;
import kr.letech.study.vo.Criteria;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	public List<Map<String, ?>> getBoardList(Criteria criteria) throws Exception{
		
		
		List<Map<String, ?>> boardList = boardRepository.selectBoardList(criteria);
		
		
		return boardList;
	}
}
