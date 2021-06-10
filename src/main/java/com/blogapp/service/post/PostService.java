package com.blogapp.service.post;

import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public interface PostService {
    Post savePost(PostDto postDto) throws PostObjectIsNullException;

    List<Post> findAllPosts();

    List<Post> postList = new ArrayList<>();

    Post updatedPost(PostDto postDto);

    Post findPostById(Integer id);

    void deletePostById(Integer id);


    Post addCommentToPost(Integer id, Comment comment);

    List<Post> findPostsInDescOrder();
}

