package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuRepository {
	
	List<Map<String, ?>> selectMenuList(Map<String, String> paraMap) throws Exception;
	
	Map<String, ?> selectMenu(Map<String, String> paraMap) throws Exception;
	
	void insertMenu(Map<String, String> paraMap)  throws Exception;
	
	void updateMenu(Map<String, String> paraMap)  throws Exception;
	
	void deleteMenu(Map<String, String> paraMap) throws Exception;
}
