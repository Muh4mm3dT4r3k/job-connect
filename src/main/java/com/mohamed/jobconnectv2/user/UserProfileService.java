package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.files.PdfFileService;
import com.mohamed.jobconnectv2.files.PhotoFileService;
import com.mohamed.jobconnectv2.files.dto.FileUploadResponse;
import com.mohamed.jobconnectv2.user.dto.CreateUserProfileRequest;
import com.mohamed.jobconnectv2.user.dto.UpdateUserProfileRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService{
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final PhotoFileService photoFileService;
    private final PdfFileService pdfFileService;

    @Transactional
    public void create(UUID id, CreateUserProfileRequest request) {
        User user = CommonMethods.findUserByIdOrThrow(id, userRepository);
        FileUploadResponse photo = photoFileService.upload(request.photo());
        FileUploadResponse cv = pdfFileService.upload(request.cv());
        UserProfile userProfile = UserProfile
                .builder()
                .id(UUID.randomUUID())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .cv(cv.filename())
                .photo(photo.filename())
                .dayOfBirth(request.dayOfBirth())
                .user(user)
                .build();
        userProfileRepository.save(userProfile);
    }

    @Transactional
    public void update(UUID id, UpdateUserProfileRequest request) {
        User user = CommonMethods.findUserByIdOrThrow(id, userRepository);

    }
}
