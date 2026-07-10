package com.himanshu.connectpro.service;

import com.himanshu.connectpro.dto.*;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

    ApiResponse<PostResponse> createPost(Long userId, CreatePostRequest request);

    ApiResponse<Page<PostResponse>> getAllPosts(int page, int size);

    ApiResponse<String> likePost(Long userId, Long postId);

    ApiResponse<String> unlikePost(Long userId, Long postId);

    ApiResponse<CommentResponse> addComment(Long userId, Long postId, CommentRequest request);

    ApiResponse<List<CommentResponse>> getComments(Long postId);
}