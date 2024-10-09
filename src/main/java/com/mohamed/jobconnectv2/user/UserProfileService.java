package com.mohamed.jobconnectv2.user;

import com.mohamed.jobconnectv2.common.CommonMethods;
import com.mohamed.jobconnectv2.user.dto.CreateUserProfileRequest;
import com.mohamed.jobconnectv2.user.dto.UpdateUserProfileRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService{
    private final CommonMethods commonMethods;
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;

    public void create(UUID id, CreateUserProfileRequest request) {
        User user = commonMethods.findUserByIdOrThrow(id);
        UserProfile userProfile = UserProfile
                .builder()
                .id(UUID.randomUUID())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .cv(request.cv())
                .photo(request.photo())
                .dayOfBirth(request.dayOfBirth())
                .build();
        user.setUserProfile(userProfile);
        userRepository.save(user);
        userProfileRepository.save(userProfile);
    }
}
