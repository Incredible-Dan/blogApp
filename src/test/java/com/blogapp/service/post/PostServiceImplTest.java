package com.blogapp.service.post;

import com.blogapp.data.models.Post;
import com.blogapp.data.repositories.PostRepository;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

class PostServiceImplTest {
    @Mock
    PostRepository postRepository;

    @InjectMocks
    PostService postServiceImpl = new PostServiceImpl();
    Post testPost;


    @BeforeEach
    void setUp() {
        testPost = new Post();
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void whenTheSavedMethodIsCalled_rheRepositoryIsCalledOnceTest() throws PostObjectIsNullException {
        when(postServiceImpl.savePost(new PostDto())).thenReturn(testPost);
        postServiceImpl.savePost(new PostDto());

        verify(postRepository, times(1)).save(testPost);

    }
    @Test
    void whenTheFindAllMethodIsCalled_ThenReturnListsOfPost(){
        List<Post> postList = new ArrayList<>();
        when(postServiceImpl.findAllPosts()).thenReturn(postList);
    }

}