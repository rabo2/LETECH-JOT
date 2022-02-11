package kr.letech.study.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.AttachDTO;

/**
 * @packageName : kr.letech.study.utility
 * @fileName : FileUtilities.java
 * @author : LE_TECH
 * @date : 2022.02.11
 * @description : ===========================================================
 *              DATE AUTHOR NOTE
 *              -----------------------------------------------------------
 *              2022.02.11 LE_TECH 최초 생성
 */
@Component
public class FileUtilities {

	private final static String ROOT_PATH = Paths
			.get("D:", "STS4-workspace", "msKim-study1", "src", "main", "resources").toString();

	/**
	 * @Method : parseFileInfo
	 * @date : 2022.02.11
	 * @author : LE_TECH
	 * @return : List<AttachDTO>
	 * @description : ===========================================================
	 *              DATE AUTHOR NOTE
	 *              -----------------------------------------------------------
	 *              2022.02.11 LE_TECH 최초 생성
	 */
	public List<AttachDTO> parseFileInfo(List<MultipartFile> files) throws IOException {

		String savePath = Paths.get(ROOT_PATH, "files").toString();

		if (!new File(savePath).exists()) {
			new File(savePath).mkdir();
		}

		List<AttachDTO> attachList = new ArrayList<>();

		for (MultipartFile file : files) {
			String oriFileName = file.getOriginalFilename();

			if (oriFileName == null || "".equals(oriFileName)) {
				continue;
			}

			String uuid = UUID.randomUUID().toString();

			String filePath = Paths.get(savePath, uuid).toString();

			File newFile = new File(filePath);

			/*
			 * 파일은 확장자를 제거후에 저장 => web shell공격 jsp파일을 업로드후 cmd창을 실행시킴
			 * 
			 */
			file.transferTo(newFile);

			String fileType = oriFileName.substring(oriFileName.lastIndexOf(".") + 1).toUpperCase();

			long size = file.getSize();

			AttachDTO attach = new AttachDTO(uuid, filePath, oriFileName, fileType, size);

			attachList.add(attach);
		}
		return attachList;
	}

}
