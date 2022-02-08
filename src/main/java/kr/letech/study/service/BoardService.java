package kr.letech.study.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.Account;
import kr.letech.study.dto.AttachDTO;
import kr.letech.study.dto.Criteria;
import kr.letech.study.dto.Page;
import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.AttachRepository;
import kr.letech.study.repository.BoardRepository;
import kr.letech.study.repository.ReplyRepository;
import kr.letech.study.utility.FileUtilities;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Autowired
	private AttachRepository attachRepository;

	public Map<String, Object> getBoardList(Map<String, Object> paraMap) throws Exception {

		List<Map<String, String>> boardList = boardRepository.selectBoardList(paraMap);

		Page page = new Page();

		page.setCri((Criteria) paraMap.get("cri"));

		if (boardList.get(0) != null) {
			page.setTotalCnt(Integer.parseInt(String.valueOf(boardList.get(0).get("cnt"))));
		} else {
			page.setTotalCnt(0);
		}

		paraMap.put("boardList", boardList);
		paraMap.put("pageInfo", page);

		return paraMap;
	}

	public void regist(Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("user"));

		paraMap.put("userNo", account.getUserNo());

		boardRepository.insertBoard(paraMap);
	}

	@Transactional
	public void registImageFile(List<MultipartFile> files, Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("writer"));

		paraMap.put("userNo", account.getUserNo());

		boardRepository.insertBoard(paraMap);

		List<AttachDTO> AttachmentsList = FileUtilities.parseFileInfo(files);

		if (!AttachmentsList.isEmpty()) {
			for (int i = 0; i < AttachmentsList.size(); i++) {
				AttachDTO attachDTO = AttachmentsList.get(i+1);
				attachRepository.insertAttach(attachDTO);

				paraMap.put("uuid", attachDTO.getUuid());
				paraMap.put("ord", String.valueOf(i));

				boardRepository.insertBoardAttach(paraMap);
			}
		}
	}

	public Map<String, ?> getBoard(Map<String, String> paraMap) throws Exception {
		if (paraMap.get("from") == null) {
			boardRepository.updateViewCount(paraMap);
		}

		Map<String, Object> board = boardRepository.selectBoardByBoardNo(paraMap);
		if (board != null) {
			if (paraMap.get("user") != null && paraMap.get("user").equals(board.get("writer"))) {
				board.put("hasRole", "OK");
			} else {
				board.put("hasRole", "NO");
			}
		}
		
		if(paraMap.get("boardDev") != "CD018") {
			List<AttachDTO> attachList = attachRepository.selectAttachList(paraMap);
			
			if(attachList != null && attachList.size() > 0) board.put("attachList", attachList);
		}
		
		return board;
	}

	public void modifyBoard(Map<String, String> paraMap) throws Exception {
		boardRepository.updateBoard(paraMap);
	}

	@Transactional
	public void remove(Map<String, String> paraMap) throws Exception {
		replyRepository.deleteAllReply(paraMap);
		boardRepository.deleteBoard(paraMap);
	}

}
