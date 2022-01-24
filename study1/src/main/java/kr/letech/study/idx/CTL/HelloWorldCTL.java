package kr.letech.study.idx.CTL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloWorldCTL {
	
	@RequestMapping("/hello")
	public String helloWorld() {
		log.info("hello world 주소 : {}","입력성공");
		String str = "hello world";
		
		return str;
	}
}
