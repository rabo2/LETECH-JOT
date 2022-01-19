package kr.letech.study.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.CommonCodeRepository;
import kr.letech.study.vo.CommonCode;
import kr.letech.study.vo.Navbar;

@Service
public class CommonCodeService {

	@Autowired
	private CommonCodeRepository cmnCdRepository;
	
	public List<CommonCode> getCommonCodeList() throws Exception{
		List<CommonCode> cmdList = cmnCdRepository.selectCommonCodeList();
		
		return cmdList;
	}
	
	public void registCommonCode(CommonCode cmd) throws Exception{
		cmnCdRepository.insertCommonCode(cmd);
	}
	
	public void modifyCommandCode(CommonCode cmd) throws Exception{
		cmnCdRepository.updateCommonCode(cmd);
	}
	
	public void removeCommonCode(String cmnCd) throws Exception{
		cmnCdRepository.deleteCommonCode(cmnCd);
		
	}

	public CommonCode getCommonCode(String comnCd) throws Exception{
		CommonCode code = cmnCdRepository.selectCommonCode(comnCd);
		return code;
	}

	public List<CommonCode> getNavbarList(Navbar nav) throws Exception{
		return cmnCdRepository.selectCommonCodeByLevel(nav);
	}
}
