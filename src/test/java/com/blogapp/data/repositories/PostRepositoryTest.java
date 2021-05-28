package com.blogapp.data.repositories;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
@Sql(scripts = "classpath:db/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PostRepositoryTest {
    private final PostRepository postRepository;
    @Autowired
    PostRepositoryTest(PostRepository postRepository){
        this.postRepository = postRepository;
    }


    @BeforeEach
    void setUp() {
    }
    @Test
    void savePostToDBTest(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("This is a simple post text.");

        log.info("Created a blog post -->{}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();
    }

    @Test
    void throwsExceptionWhenSavingPostWithDuplicateData() {

        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("This is a simple post text.");

        log.info("Created a blog post -->{}", blogPost);
        postRepository.save(blogPost);
        assertThat(blogPost.getId()).isNotNull();


        Post blogPost2 = new Post();
        blogPost2.setTitle("What is Fintech?");
        blogPost2.setContent("This is a simple post text.");

        log.info("Created a blog post -->{}", blogPost2);
         assertThrows(DataIntegrityViolationException.class, () -> postRepository.save(blogPost2));

    }
    @Test
    void whenPostIsSaved_thenSaveAuthor(){
        Post blogPost = new Post();
        blogPost.setTitle("What is Fintech?");
        blogPost.setContent("This is a simple post text.");

        log.info("Created a blog post -->{}", blogPost);
//        postRepository.save(blogPost);
//        assertThat(blogPost.getId()).isNotNull();

        Author author = new Author();
        author.setLastName("John");
        author.setFirstName("Wick");
        author.setEmail("John@mail.com");
        author.setPhoneNumber("0898767543");

//        map relationships

        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post After saving -->{}", blogPost);
    }

    @Test
    void findAPostInTheDB(){
        List<Post> existingPost = postRepository.findAll();
        assertThat(existingPost).isNotNull();
        assertThat(existingPost).hasSize(5);
    }


}