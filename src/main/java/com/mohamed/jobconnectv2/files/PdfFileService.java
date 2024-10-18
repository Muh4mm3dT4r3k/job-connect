package com.mohamed.jobconnectv2.files;

import com.mohamed.jobconnectv2.files.dto.FileUploadResponse;
import com.mohamed.jobconnectv2.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class PdfFileService implements FilesService{
    private static final String EXTENSION = "pdf";
    private static final List<String> ALLOWED_MIME_TYPE = new ArrayList<>(List.of("application/pdf", "application/x-pdf", "application/acrobat", "application/vnd.pdf"));
    @Value("${application.files.upload.cv}")
    private String uploadDir;

    @Override
    public Resource download(String filename) {
        String userId = SecurityUtils.getCurrentUserId().toString();
        Path filePath = Paths
                .get(uploadDir, userId)
                .resolve(filename)
                .normalize();
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }else {
                throw new RuntimeException("not found");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileUploadResponse upload(MultipartFile file) {
        validateFile(file);
        String userId = SecurityUtils
                .getCurrentUserId()
                .toString();
        String filename = file.getOriginalFilename();
        Path userDir = Paths.get(uploadDir, userId);
        createUserDirectoryIfNotExist(userDir);
        Path filePath = resolveFilePath(userDir, filename);
        copyFileToPath(file, filePath);
        String uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("files/")
                .path(filePath.getFileName().toString())
                .toUriString();
        return new FileUploadResponse(filename, uri, getMimeType(file), getFileSize(filePath), "file upload successfully");
    }

    private long getFileSize(Path filePath) {
        try {
            return Files.size(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void copyFileToPath(MultipartFile file, Path filePath) {
        try {
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Path resolveFilePath(Path userDir, String filename) {
        Path filePath = userDir.resolve(filename);
        int count = 1;
        while (Files.exists(filePath)) {
            String newfilename = String.format("%s(%d)", filename.substring(0, filename.lastIndexOf('.')), count)
                    + filename.substring(filename.lastIndexOf('.'));
            filePath = userDir.resolve(newfilename);
        }
        return filePath;
    }

    private void createUserDirectoryIfNotExist(Path userDir) {
        try {
            if (Files.exists(userDir)) {
                Files.createDirectories(userDir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMimeType(MultipartFile file) {
        try {
            Path tempFile = Files.createTempFile("tmp", file.getOriginalFilename());
            file.transferTo(tempFile);
            String contentType = Files.probeContentType(tempFile);
            Files.delete(tempFile);
            return contentType;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getExtension(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null && filename.contains(".")) {
            return filename
                    .substring(filename
                            .lastIndexOf('.') + 1)
                    .toLowerCase();
        }
        return "";
    }

    private boolean isValidMimeType(String mimeType) {
        return ALLOWED_MIME_TYPE.contains(mimeType);
    }

    private boolean isValidExtension(String extension) {
        return EXTENSION.equals(extension);
    }

    private boolean isPdf(MultipartFile file) {
        String extension = getExtension(file);
        String mimeType = getMimeType(file);
        return isValidExtension(extension) && isValidMimeType(mimeType);
    }

    private void validateFile(MultipartFile file) {
        if (!isPdf(file))
            throw new IllegalArgumentException("file not pdf");
    }


}
