package com.mohamed.jobconnectv2.files;

import com.mohamed.jobconnectv2.files.dto.FileUploadResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesService {
    Resource download(String filename);
    FileUploadResponse upload(MultipartFile file);
}
