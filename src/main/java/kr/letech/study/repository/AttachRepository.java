package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.dto.AttachDTO;

@Mapper
public interface AttachRepository {
	List<AttachDTO> selectAttachList(Map<String, String> paraMap) throws Exception;
	
	AttachDTO selectAttach(Map<String, String> paraMap) throws Exception;
	
	void insertAttach(AttachDTO attachDTO) throws Exception;
	
	void updateAttach(Map<String, String> paraMap) throws Exception;
	
	void deleteAttach(Map<String, String> paraMap) throws Exception;
}
