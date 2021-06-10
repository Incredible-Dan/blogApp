package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.service.cloud.CloudStorageService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PostServiceImpl  implements PostService{

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CloudStorageService cloudStorageService;

    @Override
    public Post savePost(PostDto postDto) throws PostObjectIsNullException {

        if (postDto == null) {
            throw new PostObjectIsNullException("Post cannot be null");
        }

        log.info("Post DTO -->{}",postDto);

        Post post = new Post();

        if(postDto.getCoverImage() != null && !postDto.getCoverImage().isEmpty()){
            try {
                Map<?, ?> uploadResult = cloudStorageService.uploadImage(postDto.getCoverImage(), ObjectUtils.asMap("public_id",
                        "blogapp/" + postDto.getCoverImage().getName(), "overwrite", true));

                post.setCoverImage(String.valueOf(uploadResult.get("url")));
                log.info("Image url --> {}", uploadResult.get("url"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());


        log.info("Post object after mapping --> {}", post);

        try {
            return postRepository.save(post);
        }catch (DataIntegrityViolationException ex){
            log.info("Exception occurred -->{}", ex.getMessage());
            throw ex;
        }
    }
    @Override
    public List<Post> findAllPosts()
    {
        return postRepository.findAll();
    }

    @Override
    public Post updatedPost(PostDto postDto)
    {
        return null;
    }

    @Override
    public Post findPostById(Integer id) {
        return null;
    }

    @Override
    public void deletePostById(Integer id) {

    }

    @Override
    public Post addCommentToPost(Integer id, Comment comment)
    {
        return null;
    }

    @Override
    public List<Post> findPostsInDescOrder() {
        return postRepository.findByOrderByDatePublishedDesc();
    }
}
