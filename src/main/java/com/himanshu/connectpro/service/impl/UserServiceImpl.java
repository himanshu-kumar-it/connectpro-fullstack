package com.himanshu.connectpro.service.impl;

import com.himanshu.connectpro.dto.*;
import com.himanshu.connectpro.entity.Role;
import com.himanshu.connectpro.entity.User;
import com.himanshu.connectpro.exception.DuplicateResourceException;
import com.himanshu.connectpro.exception.ResourceNotFoundException;
import com.himanshu.connectpro.mapper.UserMapper;
import com.himanshu.connectpro.repository.RoleRepository;
import com.himanshu.connectpro.repository.UserRepository;
import com.himanshu.connectpro.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse<RegisterResponse> register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already exists");
        }

        if (request.getPhone() != null && userRepository.existsByPhone(request.getPhone())) {
            throw new DuplicateResourceException("Phone number already exists");
        }

        User user = UserMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEnabled(true);

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Default role not found"));

        Set<Role> roles = new HashSet<>();
        roles.add(userRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return ApiResponse.<RegisterResponse>builder()
                .success(true)
                .message("User registered successfully")
                .data(UserMapper.toResponse(savedUser))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<UserSummaryResponse> getProfile(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return ApiResponse.<UserSummaryResponse>builder()
                .success(true)
                .message("Profile fetched successfully")
                .data(UserMapper.toSummary(user))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<UserSummaryResponse> updateProfile(Long userId, ProfileRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setHeadline(request.getHeadline());
        user.setAbout(request.getAbout());
        user.setLocation(request.getLocation());
        user.setProfileImage(request.getProfileImage());
        user.setCoverImage(request.getCoverImage());

        User updatedUser = userRepository.save(user);

        return ApiResponse.<UserSummaryResponse>builder()
                .success(true)
                .message("Profile updated successfully")
                .data(UserMapper.toSummary(updatedUser))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<Page<UserSummaryResponse>> searchUsers(String keyword, int page, int size, String sortBy) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

        Page<User> users = userRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                        keyword, keyword, keyword, pageable
                );

        Page<UserSummaryResponse> response = users.map(UserMapper::toSummary);

        return ApiResponse.<Page<UserSummaryResponse>>builder()
                .success(true)
                .message("Users fetched successfully")
                .data(response)
                .timestamp(LocalDateTime.now())
                .build();
    }
}