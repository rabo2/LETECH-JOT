package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.dto.CommonCode;
import kr.letech.study.repository.CommonCodeRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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

	public List<CommonCode> getCommonCodeListByUpCode(Map<String, String> paraMap) throws Exception{
		return cmnCdRepository.selectCommonCodeByUpCode(paraMap);
	}
	
	
	public List<CommonCode> getNavbarList(CommonCode comn) throws Exception{
		List<CommonCode> navList = cmnCdRepository.selectCommonCodeByLevel(comn);
		
		for (CommonCode commonCode : navList) {
			commonCode.setUpCd(commonCode.getComnCd());
			commonCode.setLvl(commonCode.getLvl()+1);
			
			List<CommonCode> subList = cmnCdRepository.selectCommonCodeByLevel(commonCode);

			commonCode.setLvl(commonCode.getLvl()-1);
			if(subList == null || subList.size() == 0) {
				commonCode.setSubList(null);
			}else {
				commonCode.setSubList(subList);
			}
		}
		return navList;
	}
	
}
