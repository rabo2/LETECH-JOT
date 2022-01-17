package kr.letech.study.vo;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Service
public class UserAuth {
	private String userNo;
	private String authCd;
	private boolean authDspr;
	
}
