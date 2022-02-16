package kr.letech.study.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class CommonCode {
	
	private String comnCd;
	private String upCd;
	private int lvl;
	private String val;
	private String cdNm;
	private int ord;
	private String description;
	private int collapseYn;
	
	
	private List<CommonCode> subList;

}
