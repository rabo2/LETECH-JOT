package kr.letech.study.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.Account;
import kr.letech.study.dto.AttachDTO;
import kr.letech.study.repository.AccountRepository;
import kr.letech.study.repository.AttachRepository;
import kr.letech.study.repository.BoardRepository;
import kr.letech.study.repository.ReplyRepository;
import kr.letech.study.utility.FileUtility;
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
	private FileUtility fileUtilities;

	@Value("${custom.path.file}")
	private String filePath;

	@Value("${custom.path.summernote}")
	private String summernotePath;

	@Value("${custom.path.thumbnail}")
	private String thumbnailPath;

	private static String[] FILE_TYPE = { "JPG", "JPEG", "GIF", "PNG" };

	/**
	 * @Method : getBoardList
	 * @date : 2022.02.15
	 * @author : mskim
	 * @return : List<Map<String,String>>
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.15 mskim 최초 생성
	 */
	public List<Map<String, String>> getBoardList(Map<String, Object> paraMap) throws Exception {
		List<Map<String, String>> boardList = boardRepository.selectBoardList(paraMap);
		Pattern nonValidPattern = Pattern.compile("<img[^>]*src=[\"']?([^>\"']+)[\"']?[^>]*>");

		if (boardList.size() > 0 && boardList.get(0).get("boardDev").equals("CD0019")) {
			for (Map<String, String> board : boardList) {

				Matcher matcher = nonValidPattern.matcher(board.get("content"));
				while (matcher.find()) {
					String imgTag = matcher.group(1);
					board.put("thumbnail", imgTag);
				}
			}
		}
		return boardList;
	}

	/**
	 * @Method : regist
	 * @date : 2022.02.15
	 * @author : mskim
	 * @return : void
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.15 mskim 최초 생성
	 */
	@Transactional(rollbackFor = { SQLException.class, IOException.class })
	public void regist(List<MultipartFile> files, Map<String, String> paraMap) throws Exception {
		Account account = accountRepository.selectAccount(paraMap.get("user"));

		paraMap.put("userNo", account.getUserNo());

		boardRepository.insertBoard(paraMap);

		List<AttachDTO> AttachmentsList = null;

		if (!CollectionUtils.isEmpty(files)) {
			AttachmentsList = fileUtilities.parseFileInfo(files, filePath);

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

	/**
	 * @Method : getBoard
	 * @date : 2022.02.15
	 * @author : mskim
	 * @return : Map<String,?>
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.15 mskim 최초 생성
	 */
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

	/**
	 * @Method : modifyBoard
	 * @date : 2022.02.15
	 * @author : mskim
	 * @return : void
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.15 mskim 최초 생성
	 */
	public void modifyBoard(Map<String, String> paraMap) throws Exception {
		boardRepository.updateBoard(paraMap);
	}

	/**
	 * @Method : remove
	 * @date : 2022.02.15
	 * @author : mskim
	 * @return : void
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.15 mskim 최초 생성
	 */
	@Transactional
	public void remove(Map<String, String> paraMap) throws Exception {
		replyRepository.deleteAllReply(paraMap);
		boardRepository.deleteBoard(paraMap);
	}

	public AttachDTO insertBoardImage(List<MultipartFile> files) throws Exception {
		List<AttachDTO> attachmentsList = null;

		if (!CollectionUtils.isEmpty(files)) {
			attachmentsList = fileUtilities.parseFileInfo(files, summernotePath);

			if (!attachmentsList.isEmpty()) {
				for (int i = 0; i < attachmentsList.size(); i++) {
					AttachDTO attachDTO = attachmentsList.get(i);
					attachRepository.insertAttach(attachDTO);

					attachDTO.setUploadPath("/summernoteImage/" + attachDTO.getUuid());
				}
			}
		}

		return attachmentsList.get(0);
	}

}
