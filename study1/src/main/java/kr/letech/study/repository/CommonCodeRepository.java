package kr.letech.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.vo.CommonCode;
import kr.letech.study.vo.Navbar;

@Mapper
public interface CommonCodeRepository {
	
	// 공통 코드 관리
	List<CommonCode> selectCommonCodeList();
	
	void insertCommonCode(CommonCode cmn);
	
	void updateCommonCode(CommonCode cmn);
	
	void deleteCommonCode(String comnCd);

	CommonCode selectCommonCode(String comnCd);

	
	//상단 메뉴 관리
	List<CommonCode> selectCommonCodeByLevel(Navbar nav);


}
