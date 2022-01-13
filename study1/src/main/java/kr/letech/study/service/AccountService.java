package kr.letech.study.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.AccountRepository;
import kr.letech.study.vo.Account;
import lombok.extern.slf4j.Slf4j;

@Service
public class AccountService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Account account = accountRepository.readAccount(username);
		if(account == null) {
			throw new UsernameNotFoundException(username + "is Not Found!!");
		}
		
		Collection<GrantedAuthority> authorities = getAuthorities(username);
		if(authorities != null) {
			account.setAuthorities(authorities);
		}

		return account;
	}

	public Collection<GrantedAuthority> getAuthorities(String username) {
		List<String> authorities = accountRepository.readAutorities(username);
		List<GrantedAuthority> gratendAuth = new ArrayList<GrantedAuthority>();
		
		if(authorities != null) for (String authority : authorities) {
			gratendAuth.add(new SimpleGrantedAuthority(authority));
		}
		return gratendAuth;
	}

}
