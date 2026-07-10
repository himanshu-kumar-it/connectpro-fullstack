
package com.himanshu.connectpro.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostResponse {

    private Long id;

    private String content;

    private String imageUrl;

    private String authorName;

    private LocalDateTime createdAt;
}