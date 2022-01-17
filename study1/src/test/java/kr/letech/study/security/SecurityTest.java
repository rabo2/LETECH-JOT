package kr.letech.study.security;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.FilmRepository;
import kr.letech.study.vo.Account;

public class SecurityTest {
	

	final static String DRIVER = "org.mariadb.jdbc.Driver";
	final static String URL = "jdbc:mariadb://10.10.250.242:3306/mydb";
	final static String USER = "letech";
	final static String PASSWORD = "Dpfdlxpzm08!";

	@Mock
	private FilmRepository filmRepository;

	public void getDriverConnection() throws Exception {
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS WHERE ID = ?");
			
			
			System.out.println(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Mock
	AccountRepository accountRepository;
	
	@Mock
	PasswordEncoder pwdEncoder;
	
	Authentication authentication;
	
	@Test
	public void loginTest() {
		String id = "1234";
		String pwd = "1234";
		
		Account user = accountRepository.selectAccount(id);
		if(user == null) {
			throw new UsernameNotFoundException("Wrong name");
		}
		
		if(!pwdEncoder.matches(pwd,user.getPassword())) {
			throw new BadCredentialsException("Wrong password");
		}
		
	}
	
}
