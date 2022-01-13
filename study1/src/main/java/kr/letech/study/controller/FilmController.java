package kr.letech.study.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import kr.letech.study.service.FilmService;
import kr.letech.study.vo.Film;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/film")
public class FilmController {
	
	@Autowired
	private FilmService filmService;
	
	@GetMapping("/list")
	public ModelAndView allFilm(ModelAndView mnv){
		log.info("allfilm = {}", filmService.getFilms());
		
		mnv.setViewName("film/list");
		
		Map<String, List<Film>> dataMap = filmService.getFilms();
		
		mnv.addAllObjects(dataMap);
		
		return mnv;
		
	}
}
