package com.mohamed.jobconnectv2.security;

import com.mohamed.jobconnectv2.user.User;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

public class SecurityUtils {
    public static void checkIfUserIdBelongsToCurrentUser(UUID id) {
        if (!getCurrentUserId().equals(id))
            throw new SecurityException("User ID does not belong to the current user");
    }

    public static UUID  getCurrentUserId() {
        return ((User)
                SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getDetails())
                .getId();
    }
}
