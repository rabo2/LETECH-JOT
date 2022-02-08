package kr.letech.study.dto;

import groovy.transform.builder.Builder;
import lombok.Data;

@Data
public class AttachDTO {
	private String uuid;
	private String uploadPath;
	private String fileNm;
	private String fileType;
	private String useYn;
	private double fileSize;

	@Builder
	public AttachDTO(String uuid, String uploadPath, String fileNm, String fileType, double fileSize) {
		this.uuid = uuid;
		this.uploadPath = uploadPath;
		this.fileSize = fileSize;
		this.fileNm = fileNm;
		this.fileType = fileType;
	}
}
