
package com.himanshu.connectpro.mapper;

import com.himanshu.connectpro.dto.CommentResponse;
import com.himanshu.connectpro.entity.Comment;

public class CommentMapper {

    private CommentMapper() {
    }

    public static CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .authorName(comment.getUser().getFirstName() + " " + comment.getUser().getLastName())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}