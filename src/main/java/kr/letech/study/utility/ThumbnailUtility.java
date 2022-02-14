package kr.letech.study.utility;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

@Component
public class ThumbnailUtility {
	
	private int defaultWidth = 80;
	private int defaultHeight = 80;

	public byte[] makeThumbnail(Map<String, String> paraMap) throws Exception{
		byte[] byteArray = null;
		FileInputStream inputFile = null;

		if (paraMap.get("uploadPath") != null
				&& (!paraMap.get("uploadPath").equals("undefined") && !paraMap.get("uploadPath").equals("null"))) {

			File file = new File(paraMap.get("uploadPath"));
			
			try {
				inputFile = new FileInputStream(file);
				if (inputFile != null) {
					BufferedImage resizeToThumbnail = resizeToThumbnail(inputFile);
					
				    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
				    ImageIO.write(resizeToThumbnail, "PNG", byteArrayOutputStream);
					
				    byteArray =byteArrayOutputStream.toByteArray();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputFile != null) {
					try {
						inputFile.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return byteArray;
	}
	
	
	private BufferedImage resizeToThumbnail(FileInputStream file) throws IOException {
		 
		BufferedImage srcImg = ImageIO.read(file);

		int originalWidth = srcImg.getWidth();
		int orginalHeight = srcImg.getHeight();

		// 원본 너비를 기준으로 하여 썸네일의 비율로 높이를 계산합니다.
		int newWidth = originalWidth;
		int newHeight = (originalWidth * defaultHeight) / defaultWidth;
		
		// 계산된 높이가 원본보다 높다면 crop이 안되므로
		// 원본 높이를 기준으로 썸네일의 비율로 너비를 계산합니다.
		if (newHeight > orginalHeight) {
		newWidth = (orginalHeight * defaultWidth) / defaultHeight;
		newHeight = orginalHeight;
		}
		// 계산된 크기로 원본이미지를 가운데에서 crop 합니다.
		BufferedImage cropImg = Scalr.crop(srcImg, (originalWidth - newWidth) / 2, (orginalHeight - newHeight) / 2, newWidth, newHeight);
		// crop된 이미지로 썸네일을 생성합니다.
		BufferedImage destImg = Scalr.resize(cropImg, defaultWidth, defaultHeight);
		
		return destImg;
	}
}
