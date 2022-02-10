package kr.letech.study.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.dto.CommonCode;
import kr.letech.study.dto.Navbar;

@Mapper
public interface CommonCodeRepository {
	
	// 공통 코드 관리
	
	void insertCommonCode(CommonCode cmn);
	
	void updateCommonCode(CommonCode cmn);
	
	void deleteCommonCode(String comnCd);

	CommonCode selectCommonCode(String comnCd);

	public List<CommonCode> selectCommonCodeByUpCode(Map<String, String> paraMap);
	
	//상단 메뉴 관리
	List<CommonCode> selectCommonCodeByLevel(CommonCode cmn);

	List<CommonCode> selectCommonCodeList();


}
