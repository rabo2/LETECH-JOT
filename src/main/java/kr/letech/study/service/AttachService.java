package kr.letech.study.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.dto.AttachDTO;
import kr.letech.study.repository.AttachRepository;

@Service
public class AttachService {
	
	@Autowired
	private AttachRepository attachRepository;
	
	public AttachDTO getAttach(Map<String, String> paraMap) throws Exception {
		AttachDTO attach = attachRepository.selectAttach(paraMap);
		return attach;
	}
}
