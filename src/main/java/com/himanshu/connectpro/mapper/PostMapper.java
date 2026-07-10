
package com.himanshu.connectpro.mapper;

import com.himanshu.connectpro.dto.PostResponse;
import com.himanshu.connectpro.entity.Post;

public class PostMapper {

    private PostMapper() {
    }

    public static PostResponse toResponse(Post post) {

        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .imageUrl(post.getImageUrl())
                .authorName(post.getUser().getFirstName() + " " + post.getUser().getLastName())
                .createdAt(post.getCreatedAt())
                .build();

    }

}