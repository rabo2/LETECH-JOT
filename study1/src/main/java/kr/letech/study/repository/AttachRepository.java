package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AttachRepository {
	List<Map<String, ?>> selectAttachList(Map<String, String> paraMap) throws Exception;
	
	Map<String,?> selectAttach(Map<String, String> paraMap) throws Exception;
	
	void insertAttach(Map<String, String> paraMap) throws Exception;
	
	void updateAttach(Map<String, String> paraMap) throws Exception;
	
	void deleteAttach(Map<String, String> paraMap) throws Exception;
	
}
