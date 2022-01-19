package kr.letech.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.vo.CommonCode;

@Mapper
public interface CommonCodeRepository {
	
	List<CommonCode> selectCommonCodeList();
	
	void insertCommonCode(CommonCode cmn);
	
	void updateCommonCode(CommonCode cmn);
	
	void deleteCommonCode(String comnCd);

	CommonCode selectCommonCode(String comnCd);
}
