package kr.letech.study.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;

public class FilmRepositoryTest {

	final static String DRIVER = "org.mariadb.jdbc.Driver";
	final static String URL = "jdbc:mariadb://10.10.250.242:3306/mydb";
	final static String USER = "letech";
	final static String PASSWORD = "Dpfdlxpzm08!";

	@Mock
	private FilmRepository filmRepository;

	@Test
	public void getDriverConnection() throws Exception {
		Class.forName(DRIVER);
		try {
			Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
			Statement statement = connection.createStatement();

			ResultSet resultSet = statement.executeQuery("SELECT * FROM film");

			System.out.println(resultSet);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
