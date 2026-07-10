package com.himanshu.connectpro.service;

import com.himanshu.connectpro.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {

    ApiResponse<RegisterResponse> register(RegisterRequest request);

    ApiResponse<UserSummaryResponse> getProfile(Long userId);

    ApiResponse<UserSummaryResponse> updateProfile(Long userId, ProfileRequest request);

    ApiResponse<Page<UserSummaryResponse>> searchUsers(String keyword, int page, int size, String sortBy);
}
