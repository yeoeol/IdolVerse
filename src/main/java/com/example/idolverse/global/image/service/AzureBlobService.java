package com.example.idolverse.global.image.service;

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

	public String uploadFile(MultipartFile file, String containerName) {
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		if (!containerClient.exists()) {
			containerClient.create();
		}

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
}
