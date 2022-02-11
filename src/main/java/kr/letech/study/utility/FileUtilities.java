package kr.letech.study.utility;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.letech.study.dto.AttachDTO;

@Component
public class FileUtilities {
	
	/*
	 * yaml파일 환경변수로 명시
	 * spring cloud
	 */
	private final static String ROOT_PATH = Paths.get("D:", "STS4-workspace", "msKim-study1","src","main", "resources").toString();

	/**
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public List<AttachDTO> parseFileInfo(List<MultipartFile> files) throws Exception {
		if (CollectionUtils.isEmpty(files)) {
			return Collections.emptyList();
		}

		String savePath = Paths.get(ROOT_PATH, "files").toString();

		if (!new File(savePath).exists()) {
			
			try {
				new File(savePath).mkdir();
			} catch (Exception e) {
//				예외 발생시 처리해야하는 부분을 지정
			}
		}
	
		List<AttachDTO> attachList = new ArrayList<>();

		for (MultipartFile file : files) {
			String oriFileName = file.getOriginalFilename();
			
			if(oriFileName == null || "".equals(oriFileName)) {
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
			
			String fileType = oriFileName.substring(oriFileName.lastIndexOf(".")+1).toUpperCase();
		
			long size = file.getSize();
			
			AttachDTO attach = new AttachDTO(uuid, filePath, oriFileName, fileType, size);
			
			attachList.add(attach);
		}
		return attachList;
	}

}
