package kr.letech.study.vo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Data
@ToString
public class UserInfoVo {
	private String userNo;
	private String id;
	private String userNm;
	private String password;
	private String authCd;
}
