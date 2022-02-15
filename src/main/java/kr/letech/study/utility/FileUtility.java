package kr.letech.study.utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.AttachDTO;
import lombok.extern.slf4j.Slf4j;

/**
* @packageName 		: kr.letech.study.utility 
* @fileName 		: FileUtilities.java 
* @author 			: mskim 
* @date 			: 2022.02.15
* @description      :
* =========================================================== 
* DATE 				AUTHOR 			NOTE 
* ----------------------------------------------------------- 
* 2022.02.15 		mskim			최초 생성
*/
@Component
@Slf4j
public class FileUtility {

	@Value("${custom.file.path}")
	private String filePath;

	/**
	* @Method 			: parseFileInfo
	* @date 			: 2022.02.15
	* @author 			: mskim
	* @return			: List<AttachDTO>
	* @description		:
	* =========================================================== 
	* DATE 				AUTHOR 			NOTE 
	* ----------------------------------------------------------- 
	* 2022.02.15		mskim			최초 생성
	*/
	public List<AttachDTO> parseFileInfo(List<MultipartFile> files) throws IOException {
		
		filePath = filePath.replace("/", File.separator);
		String savePath = Paths.get(filePath, "files").toString();

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

			file.transferTo(newFile);

			String fileType = oriFileName.substring(oriFileName.lastIndexOf(".") + 1).toUpperCase();

			long size = file.getSize();

			AttachDTO attach = new AttachDTO(uuid, filePath, oriFileName, fileType, size);

			attachList.add(attach);
		}
		return attachList;
	}

}
