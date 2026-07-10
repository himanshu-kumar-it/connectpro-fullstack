package com.himanshu.connectpro.mapper;

import com.himanshu.connectpro.dto.RegisterRequest;
import com.himanshu.connectpro.dto.RegisterResponse;
import com.himanshu.connectpro.dto.UserSummaryResponse;
import com.himanshu.connectpro.entity.User;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(RegisterRequest request) {

        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();

    }

    public static RegisterResponse toResponse(User user) {

        return RegisterResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();

    }

    public static UserSummaryResponse toSummary(User user) {

        return UserSummaryResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .headline(user.getHeadline())
                .location(user.getLocation())
                .profileImage(user.getProfileImage())
                .build();

    }
}