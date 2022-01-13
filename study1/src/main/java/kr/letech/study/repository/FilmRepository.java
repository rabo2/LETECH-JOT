package kr.letech.study.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import kr.letech.study.vo.Film;

@Mapper
public interface FilmRepository {
	List<Film> allFilm();
}
