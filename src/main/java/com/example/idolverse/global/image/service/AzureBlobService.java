package com.example.idolverse.global.image.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.models.BlobHttpHeaders;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AzureBlobService {

	private final BlobServiceClient blobServiceClient;

	private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif");
	private static final long MAX_SIZE = 5 * 1024 * 1024; // 5MB

	public String uploadFile(MultipartFile file, String containerName) {
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		if (!containerClient.exists()) {
			containerClient.create();
		}

		validateExtension(file);
		validateFileSize(file);

		String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		BlobClient blobClient = containerClient.getBlobClient(uniqueFileName);

		try {
			BlobHttpHeaders headers = new BlobHttpHeaders().setContentType(file.getContentType());
			blobClient.upload(file.getInputStream(), file.getSize(), true);
			blobClient.setHttpHeaders(headers);
		} catch (Exception e) {
			throw new RuntimeException("파일 업로드 실패", e);
		}

		return blobClient.getBlobUrl(); // 업로드된 파일 URL 반환
	}

	private void validateExtension(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
		if (!ALLOWED_EXTENSIONS.contains(extension)) {
			throw new RuntimeException("허용되지 않는 확장자입니다.");
		}
	}

	private void validateFileSize(MultipartFile file) {
		if (file.getSize() > MAX_SIZE) {
			throw new RuntimeException("파일 크기는 5MB를 초과할 수 없습니다.");
		}
	}
}
