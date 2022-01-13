package kr.letech.study.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.FilmRepository;
import kr.letech.study.vo.Film;

@Service
public class FilmService {
	
	@Autowired
	private FilmRepository filmRepository;
	
	public Map<String, List<Film>> getFilms(){
		Map<String,List<Film>> dataMap = new HashMap<String, List<Film>>();
		
		List<Film> allFilm = filmRepository.allFilm();
		
		dataMap.put("films", allFilm);
		
		return dataMap;
	}
	
}
