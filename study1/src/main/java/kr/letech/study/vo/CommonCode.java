package kr.letech.study.vo;

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
	
	private List<CommonCode> subList;

}
