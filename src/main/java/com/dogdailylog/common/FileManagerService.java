package com.dogdailylog.common;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagerService {
	public static final String FILE_UPLOAD_PATH = "/Users/jasonmilian/Desktop/megaProject/6_spring_project/dogdailylog/workspace/images/";
	private static final Logger logger = LoggerFactory.getLogger(FileManagerService.class);
	
	public String saveFile(String loginEmail, MultipartFile file) {
		
		// 저장 path
		String dirName = loginEmail + "_" +  System.currentTimeMillis() + "/";// 사용자의 id + 저장한 시간으로 폴더명을 지음
		String filePath = FILE_UPLOAD_PATH + dirName;
		
		File directory = new File(filePath);
		
		// 폴더 만드는 데 실패했을 
		if( directory.mkdir() == false ) {
			return null;
		}
		String ogName = file.getOriginalFilename();
		
		logger.debug("[file name] file : {}"+ file.getOriginalFilename());		
		logger.debug("[ext name] ext: {}" + ogName.split("[.]")[1]);
		
		// . 으로 split 할때 []안에 .을 넣어 연산해야함 or 역슬래시(\) 다음에 오는 문자를 일반 문자로 취급 +escape
		String ext = ogName.split("\\.")[1]; // 확장자를 꺼냄
		// 기존 파일명을 내가 원하는 파일명으로 정하기
		String fileName = file.getOriginalFilename().replaceAll(file.getOriginalFilename(), "profileImg." + ext);
		
		try {
			byte[] bytes = file.getBytes();
		
			Path path = Paths.get(filePath + fileName);
			Files.write(path, bytes); // 파일 업로드
					
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return "/images/" + dirName + fileName;
	}
	
	public void deleteFile(String imagePath) {
		// 겹치는 Images경로를 제거
		// imagePath 안에있는 /images/ 제거
		Path path = Paths.get(FILE_UPLOAD_PATH + imagePath.replace("/images/", ""));
		
		// 이미지를 제거하고 폴더를 제거해줘야함
		if (Files.exists(path)) {
			// 부른쪽에서 해결하지 않고 여기서 해결함
			try {
				Files.delete(path);
			} catch (IOException e) {
				logger.error("[delete file] 이미지 삭제 실패. imagePath : {}", imagePath);
				return; // 수행안한다.
			}
			
		} 
		
		// 디렉토리 삭제
		path = path.getParent(); // 이미지가 있는 상위폴더
		if (Files.exists(path)) {
			try {
				Files.delete(path); // 폴더 삭제
			} catch (IOException e) {
				logger.error("[delete dir] 폴더 삭제 실패. path : {}", path);
				return;
			}
		}
	}
}
