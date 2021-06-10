package com.blogapp.web.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;

@Data
public class PostDto {

    @NotEmpty(message = "Post Title cannot be null")
    private String title;

    @NotEmpty(message = "Post Content cannot be null")
    private String content;

    private MultipartFile coverImage;

}
