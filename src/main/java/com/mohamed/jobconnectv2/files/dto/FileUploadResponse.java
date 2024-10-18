package com.mohamed.jobconnectv2.files.dto;

public record FileUploadResponse(
        String filename,
        String fileUrl,
        String fileType,
        Long size,
        String message
) {
}
