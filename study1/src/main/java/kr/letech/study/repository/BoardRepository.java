package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.vo.Criteria;

@Mapper
public interface BoardRepository {
	
	List<Map<String, ?>> selectBoardList(Criteria criteria) throws Exception;
	
	Map<String, Object> selectBoardByBoardNo(Map<String, String> paraMap) throws Exception;
	
	void insertBoard(Map<String, String> paraMap) throws Exception;
	
	void updateBoard(Map<String, String> paraMap) throws Exception;
	
	void deleteBoard(Map<String, String> paraMap) throws Exception;

	void updateViewCount(Map<String, String> paraMap);
}
