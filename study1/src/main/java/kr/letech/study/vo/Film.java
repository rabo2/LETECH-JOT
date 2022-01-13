package kr.letech.study.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
//@Alias("film")
public class Film {
	private String filmId;
	private String title;
	private String description;
	private String releaseYeaer;
	private String languageId;
	private String orginalLanguageId;
	private String rentalDuration;
	private String rentalRate;
	private String length;
	private String replacementCost;
	private String rating;
	private String specialFeatures;
	private String lastUpdate;
	
	
}
