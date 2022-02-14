package kr.letech.study.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.Account;
import kr.letech.study.dto.AttachDTO;
import kr.letech.study.dto.CommonCode;
import kr.letech.study.dto.Criteria;
import kr.letech.study.dto.Page;
import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.AttachRepository;
import kr.letech.study.repository.BoardRepository;
import kr.letech.study.repository.CommonCodeRepository;
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

	@Autowired
	private FileUtilities fileUtilities;

	private static String[] FILE_TYPE = { "JPG", "JPEG", "GIF", "PNG"};

	public List<Map<String, String>> getBoardList(Map<String, Object> paraMap) throws Exception {
		List<Map<String, String>> boardList = boardRepository.selectBoardList(paraMap);

		if (boardList != null && boardList.size() > 0) {
			if (boardList.get(0).get("boardDev").equals("CD019")) {
				log.info("boardNm : 이미지");
				for (Map<String, String> board : boardList) {
					List<AttachDTO> attachList = attachRepository.selectAttachList(board);
					if (attachList != null && attachList.size() > 0) {
						String fileType = attachList.get(0).getFileType();
						if (Arrays.asList(FILE_TYPE).contains(fileType)) {
							board.put("uploadPath", attachList.get(0).getUploadPath());
						}else {
							board.put("uploadPath", null);
						}
					}
				}
			}
		}

		return boardList;
	}

	@Transactional
	public void regist(List<MultipartFile> files, Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("user"));

		paraMap.put("userNo", account.getUserNo());

		boardRepository.insertBoard(paraMap);

		List<AttachDTO> AttachmentsList = null;
		
		if (!CollectionUtils.isEmpty(files)) {
			AttachmentsList = fileUtilities.parseFileInfo(files);

			if (!AttachmentsList.isEmpty()) {
				for (int i = 0; i < AttachmentsList.size(); i++) {
					AttachDTO attachDTO = AttachmentsList.get(i);
					attachRepository.insertAttach(attachDTO);

					paraMap.put("uuid", attachDTO.getUuid());
					paraMap.put("ord", String.valueOf(i) + 1);

					boardRepository.insertBoardAttach(paraMap);
				}

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

		List<AttachDTO> attachList = attachRepository.selectAttachList(paraMap);
		board.put("attachList", attachList);

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
