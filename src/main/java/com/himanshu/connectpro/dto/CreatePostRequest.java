
package com.himanshu.connectpro.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreatePostRequest {

    @NotBlank(message = "Content is required")
    private String content;

    private String imageUrl;
}