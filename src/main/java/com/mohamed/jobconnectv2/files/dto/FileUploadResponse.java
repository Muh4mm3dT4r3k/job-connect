package com.mohamed.jobconnectv2.files.dto;

public record FileUploadResponse(
        String fileName,
        String fileUrl,
        String fileType,
        Long size,
        String message
) {
}
