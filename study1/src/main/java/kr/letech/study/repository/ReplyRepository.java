package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyRepository {
	
	public List<Map<String, ?>> selectReplyByBoardNo(Map<String, String> paraMap);
	
	public void insertReply(Map<String, String> paraMap);
	
	public void updateReply(Map<String, String> paraMap);
	
	public void deleteReply(Map<String, String> paraMap);

	public void deleteAllReply(Map<String, String> paraMap);
}
