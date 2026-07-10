package com.himanshu.connectpro.controller;

import com.himanshu.connectpro.dto.*;
import com.himanshu.connectpro.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(
            @PathVariable Long userId,
            @Valid @RequestBody CreatePostRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.createPost(userId, request));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<PostResponse>>> getAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return ResponseEntity.ok(postService.getAllPosts(page, size));
    }

    @PostMapping("/{postId}/like/user/{userId}")
    public ResponseEntity<ApiResponse<String>> likePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(postService.likePost(userId, postId));
    }

    @DeleteMapping("/{postId}/like/user/{userId}")
    public ResponseEntity<ApiResponse<String>> unlikePost(
            @PathVariable Long postId,
            @PathVariable Long userId) {

        return ResponseEntity.ok(postService.unlikePost(userId, postId));
    }

    @PostMapping("/{postId}/comments/user/{userId}")
    public ResponseEntity<ApiResponse<CommentResponse>> addComment(
            @PathVariable Long postId,
            @PathVariable Long userId,
            @Valid @RequestBody CommentRequest request) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(postService.addComment(userId, postId, request));
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getComments(postId));
    }
}