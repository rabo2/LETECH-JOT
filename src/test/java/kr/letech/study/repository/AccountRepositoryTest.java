package kr.letech.study.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import kr.letech.study.vo.UserInfoVo;

public class AccountRepositoryTest {

	final static String DRIVER = "org.mariadb.jdbc.Driver";
	final static String URL = "jdbc:mariadb://10.10.250.242:3306/ojtdb";
	final static String USER = "letech";
	final static String PASSWORD = "Dpfdlxpzm08!";

	@Mock
	private AccountRepository accountRepository;

	@Test
	public void getDriverConnection() throws Exception {
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

			System.out.println(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void insertTest() throws Exception {
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();
			
			int result = statement.executeUpdate("INSERT INTO USERS(ID, PASSWORD, USER_NM) VALUES('1234','1234','1234')");
			System.out.println(result);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
