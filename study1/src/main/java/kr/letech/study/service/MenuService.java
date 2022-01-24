package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.letech.study.repository.MenuRepository;

@Service
public class MenuService {

	@Autowired
	private MenuRepository menuRepository;

	public List<Map<String, ?>> getMenuList(Map<String, String> paraMap) throws Exception {
		return menuRepository.selectMenuList(paraMap);
	}

	public Map<String, ?> getMenu(Map<String, String> paraMap) throws Exception {
		return menuRepository.selectMenu(paraMap);
	}

	public void registMenu(Map<String, String> parMap) throws Exception {
		menuRepository.insertMenu(parMap);
	}

	public void modifyMenu(Map<String, String> paraMap) throws Exception {
		menuRepository.updateMenu(paraMap);
	}
	
	public void removeMenu(Map<String, String> paraMap) throws Exception{
		menuRepository.deleteMenu(paraMap);
	}
}
