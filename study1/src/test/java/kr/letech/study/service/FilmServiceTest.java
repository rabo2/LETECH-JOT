package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import kr.letech.study.repository.FilmRepository;
import kr.letech.study.vo.Film;

public class FilmServiceTest {
	
	@InjectMocks
	private FilmService filmService;
	
	@Mock
	private FilmRepository filmRepository;
	
	@Test
	public void filmListTest() {
		Map<String, List<Film>> dataMap = filmService.getFilms();
		
		List<Film> films = dataMap.get("films");
		
		for (Film film : films) {
			System.out.println(film);
		}
		
		
	}
	
}
