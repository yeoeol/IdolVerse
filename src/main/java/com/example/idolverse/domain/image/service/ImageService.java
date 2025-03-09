package com.example.idolverse.domain.image.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.idolverse.global.exception.GeneralException;
import com.example.idolverse.global.exception.code.ErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageService {

	@Value("${file.path}")
	private String uploadFolder;

	@Transactional
	public String uploadProfileImage(MultipartFile file) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid + "_" + file.getOriginalFilename();

		Path imageFilePath = Paths.get(uploadFolder + imageFileName);
		try {
			Files.write(imageFilePath, file.getBytes());
		} catch (Exception e) {
			throw new GeneralException(ErrorCode.INTERNAL_SERVER_ERROR);
		}

		return imageFilePath.toString();
	}
}
