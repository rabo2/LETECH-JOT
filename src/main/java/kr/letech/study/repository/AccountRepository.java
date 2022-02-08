package kr.letech.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import kr.letech.study.dto.Account;
import kr.letech.study.dto.UserAuth;
import kr.letech.study.dto.UserInfoVo;
import kr.letech.study.dto.Account.AccountBuilder;

@Mapper
public interface AccountRepository {
	Account selectAccount(String id);
	
	List<UserAuth> selectAutorities(String id);
	
	@Options(useGeneratedKeys = true, keyProperty = "userNo")
	void insertAccount(Account account);

	void insertUserAuth(UserInfoVo infoVo);
}