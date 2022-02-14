package kr.letech.study.controller;

import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import kr.letech.study.dto.Criteria;
import kr.letech.study.dto.Page;
import kr.letech.study.service.BoardService;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName 		: kr.letech.study.controller 
* @fileName 		: BoardController.java 
* @author 			: LE_TECH 
* @date 			: 2022.02.11
* @description      :
* =========================================================== 
* DATE 				AUTHOR 			NOTE 
* ----------------------------------------------------------- 
* 2022.02.11 		LE_TECH			최초 생성
*/
@Slf4j
@Controller
@RequestMapping("/boards")

public class BoardController {

	@Autowired
	private BoardService boardService;

	/**
	* @Method 			: main
	* @date 			: 2022.02.14
	* @author 			: mskim
	* @return			: String
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.14		mskim			최초 생성
	*/
	@GetMapping("/CD{boardDev}")
	public String main(@PathVariable String boardDev, Model model) {
		log.info("\nboardDev : {}", boardDev);
		model.addAttribute("boardDev", "CD"+boardDev);
		return "board/main";
	}
	
	/**
	* @Method 			: printboardList
	* @date 			: 2022.02.14
	* @author 			: mskim
	* @return			: ResponseEntity<Object>
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.14		mskim			최초 생성
	*/
	@GetMapping("/boardDev{boardDev}")
	@ResponseBody
	public ResponseEntity<Object> printboardList(@PathVariable(value = "boardDev") String boardDev) throws Exception {
		ResponseEntity<Object> entity = null;
		
		Map<String, Object> paraMap = new HashMap<>();

		paraMap.put("boardDev", boardDev);

		try {
			entity = new ResponseEntity<Object>( boardService.getBoardList(paraMap), HttpStatus.OK);
		}catch (SQLException e) {
			entity = new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	/**
	* @Method 			: detail
	* @date 			: 2022.02.11
	* @author 			: LE_TECH
	* @return			: String
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.11		LE_TECH			최초 생성
	*/
	@GetMapping("/{boardDev}/{boardNo}")
	public String detail(@PathVariable Map<String, String> paraMap, Model model, Principal principal) throws Exception {

		paraMap.put("user", principal.getName());
		
		Map<String, ?> board = boardService.getBoard(paraMap);

		model.addAttribute("board", board);

		return "board/detail";
	}

	@GetMapping("/regist")
	public String registForm() {
		return "board/regist";
	}

	@PostMapping("")
	public RedirectView regist(MultipartHttpServletRequest multiRequest) throws Exception {
		List<MultipartFile> files = multiRequest.getFiles("files");
		
		Map<String, String> paraMap = new HashMap<>();
		Principal principal = multiRequest.getUserPrincipal();

		paraMap.put("title", multiRequest.getParameter("title"));
		paraMap.put("content", multiRequest.getParameter("content"));
		paraMap.put("user", principal.getName());
		paraMap.put("boardDev", multiRequest.getParameter("boardDev"));
		paraMap.put("boardClass", multiRequest.getParameter("boardClass"));

		boardService.regist(files, paraMap);

		return new RedirectView("boards/"+multiRequest.getParameter("boardDev"));
	}

	/**
	* @Method 			: modifyForm
	* @date 			: 2022.02.14
	* @author 			: mskim
	* @return			: String
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.14		mskim			최초 생성
	*/
	@GetMapping("/modify")
	public String modifyForm(@RequestParam Map<String, String> paraMap, Model model) throws Exception {
		Map<String, ?> board = boardService.getBoard(paraMap);
		model.addAttribute("board", board);
		model.addAttribute("boardDev", paraMap.get("boardDev"));

		return "board/modify";
	}

	/**
	* @Method 			: modify
	* @date 			: 2022.02.14
	* @author 			: mskim
	* @return			: String
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.14		mskim			최초 생성
	*/
	@PutMapping("")
	public String modify(@RequestBody Map<String, String> paraMap) throws Exception {
		boardService.modifyBoard(paraMap);

		return "board/modify";
	}

	/**
	* @Method 			: remove
	* @date 			: 2022.02.14
	* @author 			: mskim
	* @return			: String
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.14		mskim			최초 생성
	*/
	@DeleteMapping("")
	public String remove(@RequestBody Map<String, String> paraMap) throws Exception {
		boardService.remove(paraMap);
		return "board/detail";
	}
	
}
