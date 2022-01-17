package kr.letech.study.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.letech.study.repository.AccountRepository;
import kr.letech.study.vo.Account;
import kr.letech.study.vo.UserAuth;
import kr.letech.study.vo.UserInfoVo;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountService implements UserDetailsService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	PasswordEncoder pwdEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = accountRepository.selectAccount(username);

		if (user == null) {
			throw new UsernameNotFoundException(username + "Not Found!");
		}

		log.info("Success find user {}>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", user);

		List<UserAuth> authList = accountRepository.selectAutorities(username);
		List<GrantedAuthority> gratnedList = new ArrayList<>();
		try {
			
		if (authList != null) {
			for (UserAuth auth : authList) {
				log.info("auth_cd??????????????? {}",auth.getAuthCd());
				gratnedList.add(new SimpleGrantedAuthority(auth.getAuthCd()));
			}
			user.setAuthorities(gratnedList);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	@Transactional
	public void save(UserInfoVo infoVo) throws SQLException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		infoVo.setPassword(encoder.encode(infoVo.getPassword()));

		Account account = new Account(infoVo.getId(), infoVo.getPassword(), infoVo.getUserNm());
		UserAuth userAuth = new UserAuth();
		userAuth.setAuthCd(infoVo.getAuthCd());

		accountRepository.insertAccount(account);

		infoVo.setUserNo(account.getUserNo());
		accountRepository.insertUserAuth(infoVo);
	}

}
