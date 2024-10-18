package com.mohamed.jobconnectv2.files;

import com.mohamed.jobconnectv2.files.dto.FileUploadResponse;
import com.mohamed.jobconnectv2.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
import java.util.Objects;


@Service
public class PhotoFileService implements FilesService{
    public static List<String> ALLOWED_EXTENSIONS = new ArrayList<>(List.of("jpg", "jpeg", "png"));
    public static List<String> ALLOWED_MIMETYPES = new ArrayList<>(List.of("image/jpeg", "image/png"));
    @Value("${application.files.upload.image}")
    private String uploadDir;

    @Override
    public Resource download(String fileName) {
        String userId = SecurityUtils.getCurrentUserId().toString();
        Path filePath = Paths
                .get(uploadDir, userId)
                .resolve(fileName)
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
        String currentUserId = SecurityUtils
                .getCurrentUserId().toString();
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path userDir = Paths.get(uploadDir, currentUserId);
        createUserDirectoryIfNotExists(userDir);
        Path filePath = resolveFilePath(userDir, filename);
        copyFileToPath(file, filePath);
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("files/").path(filePath.getFileName().toString()).toUriString();
        return new FileUploadResponse(filename, uri, getMIMEType(file), getFileSize(filePath), "file upload successfully");

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
            throw new RuntimeException("can't save photo", e);
        }
    }

    private void createUserDirectoryIfNotExists(Path userDir) {
        if (!Files.exists(userDir)) {
            try {
                Files.createDirectories(userDir);
            } catch (IOException e) {
                throw new RuntimeException("could not create user directory: " + userDir, e);
            }
        }
    }


    private void validateFile(MultipartFile file) {
        if (!isImage(file))
            throw new IllegalArgumentException("The upload file is not a valid image");
    }

    private Path resolveFilePath(Path userDir, String fileName) {
        Path filePath = userDir.resolve(fileName);
        int count = 1;

        while (Files.exists(filePath)) {
            String newFileName = String.format("%s(%d)", fileName.substring(0, fileName.lastIndexOf('.')), count)
                    + fileName.substring(fileName.lastIndexOf('.'));
            filePath = userDir.resolve(newFileName);
            count ++;
        }
        return filePath;
    }

    private String getFileExtension(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName != null && fileName.contains(".")) {
            return fileName
                    .substring(fileName.charAt('.') + 1)
                    .toLowerCase();
        }
        return "";
    }

    private String getMIMEType(MultipartFile file) {
        try {
            Path tmpFile = Files
                    .createTempFile("tmp", file.getOriginalFilename());
            file.transferTo(tmpFile);
            String mimeType = Files.probeContentType(tmpFile);
            Files.delete(tmpFile);
            return mimeType;

        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: exception handling
        }
    }


    private boolean isValidImageMimeType(String mimeType) {
        return ALLOWED_MIMETYPES
                .contains(mimeType.toLowerCase());
    }

    private boolean isValidImageExtension(String extension) {
        return ALLOWED_EXTENSIONS
                .contains(extension);
    }

    private boolean isImage(MultipartFile file) {
        String fileExtension = getFileExtension(file);
        String mimeType = getMIMEType(file);
        return isValidImageExtension(fileExtension) && isValidImageMimeType(mimeType);
    }


}
