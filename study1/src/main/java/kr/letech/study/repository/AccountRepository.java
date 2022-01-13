package kr.letech.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.letech.study.vo.Account;
import kr.letech.study.vo.Auth;

@Mapper
public interface AccountRepository {
	Account readAccount(String id);
	
	List<String> readAutorities(String id);
}