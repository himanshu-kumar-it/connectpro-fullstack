package com.himanshu.connectpro.service.impl;

import com.himanshu.connectpro.dto.*;
import com.himanshu.connectpro.entity.*;
import com.himanshu.connectpro.exception.DuplicateResourceException;
import com.himanshu.connectpro.exception.ResourceNotFoundException;
import com.himanshu.connectpro.mapper.CommentMapper;
import com.himanshu.connectpro.mapper.PostMapper;
import com.himanshu.connectpro.repository.*;
import com.himanshu.connectpro.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;

    @Override
    public ApiResponse<PostResponse> createPost(Long userId, CreatePostRequest request) {
        User user = getUser(userId);

        Post post = Post.builder()
                .content(request.getContent())
                .imageUrl(request.getImageUrl())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return ApiResponse.<PostResponse>builder()
                .success(true)
                .message("Post created successfully")
                .data(PostMapper.toResponse(savedPost))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<Page<PostResponse>> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());

        Page<PostResponse> posts = postRepository.findAll(pageable)
                .map(PostMapper::toResponse);

        return ApiResponse.<Page<PostResponse>>builder()
                .success(true)
                .message("Posts fetched successfully")
                .data(posts)
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<String> likePost(Long userId, Long postId) {
        User user = getUser(userId);
        Post post = getPost(postId);

        if (likeRepository.existsByUserAndPost(user, post)) {
            throw new DuplicateResourceException("Post already liked");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();

        likeRepository.save(like);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Post liked successfully")
                .data("Total likes: " + likeRepository.countByPost(post))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<String> unlikePost(Long userId, Long postId) {
        User user = getUser(userId);
        Post post = getPost(postId);

        if (!likeRepository.existsByUserAndPost(user, post)) {
            throw new ResourceNotFoundException("Like not found");
        }

        likeRepository.deleteByUserAndPost(user, post);

        return ApiResponse.<String>builder()
                .success(true)
                .message("Post unliked successfully")
                .data("Total likes: " + likeRepository.countByPost(post))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<CommentResponse> addComment(Long userId, Long postId, CommentRequest request) {
        User user = getUser(userId);
        Post post = getPost(postId);

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();

        Comment savedComment = commentRepository.save(comment);

        return ApiResponse.<CommentResponse>builder()
                .success(true)
                .message("Comment added successfully")
                .data(CommentMapper.toResponse(savedComment))
                .timestamp(LocalDateTime.now())
                .build();
    }

    @Override
    public ApiResponse<List<CommentResponse>> getComments(Long postId) {
        Post post = getPost(postId);

        List<CommentResponse> comments = commentRepository.findByPostOrderByCreatedAtDesc(post)
                .stream()
                .map(CommentMapper::toResponse)
                .toList();

        return ApiResponse.<List<CommentResponse>>builder()
                .success(true)
                .message("Comments fetched successfully")
                .data(comments)
                .timestamp(LocalDateTime.now())
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post not found"));
    }
}