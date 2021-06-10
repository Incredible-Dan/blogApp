package com.blogapp.data.repositories;

import com.blogapp.data.models.Author;
import com.blogapp.data.models.Comment;
import com.blogapp.data.models.Post;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@Slf4j
@Sql(scripts = "classpath:db/insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Transactional
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
        author.setProfession("Senator");



//        map relationships

        blogPost.setAuthor(author);
        author.addPost(blogPost);

        postRepository.save(blogPost);
        log.info("Blog post After saving -->{}", blogPost);

        Post updatedPost = postRepository.findByTitle("What is Fintech?");
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getAuthor()).isNotNull();
//        assertThat(updatedPost.getAuthor()).getLastName().isEqual("John");
    }

    @Test
    void findAPostInTheDB(){
        List<Post> existingPost = postRepository.findAll();
        assertThat(existingPost).isNotNull();
        assertThat(existingPost).hasSize(5);
    }

    @Test
    @Rollback(value = false)
    void deletePostByIDTest(){
        Post savedPost  = postRepository.findById(42).orElse(null);
        assertThat(savedPost).isNotNull();
        log.info("Post fetched from database -->{}",savedPost);

        //delete post.
        postRepository.deleteById(savedPost.getId());

        Post deletedPost = postRepository.findById(42).orElse(null);
        assertThat(deletedPost).isNull();
        log.info("Post fetched from database -->{}",savedPost);
    }
    @Test
    void updatedSavedPostsTitleTest(){

        Post oldPost = postRepository.findById(42).orElse(null);
        assertThat(oldPost).isNotNull();
        assertThat(oldPost.getTitle()).isEqualTo("Title post 2");
        log.info("Formal post in the database -->{}", oldPost);

//        updated post title
        oldPost.setTitle("This is a new Title");
        postRepository.save(oldPost);

        Post updatedPost = postRepository.findById(oldPost.getId()).orElse(null);
        assertThat(updatedPost).isNotNull();
        assertThat(updatedPost.getTitle()).isEqualTo("This is a new Title");
    }
    @Test
    @Rollback(value = false)
    void updatePostAuthorTest(){
        Post previousPost  = postRepository.findById(41).orElse(null);
        assertThat(previousPost).isNotNull();

//        log.info("Saved post object -->{}",previousPost);

        Author author = new Author();
        author.setLastName("Michael");
        author.setFirstName("Dan");
        author.setEmail("Michael@mail.com");
        author.setPhoneNumber("0988767333");
        author.setProfession("Pastor");
        log.info("author obj --> {}", author);
        previousPost.setAuthor(author);
        postRepository.save(previousPost);

//        Post updatedPost  = postRepository.findById(previousPost.getId()).orElse(null);
        assertThat(previousPost).isNotNull();
        assertThat(previousPost.getAuthor().getLastName()).isEqualTo("Michael");
        assertThat(previousPost.getAuthor()).isEqualTo(author);
        log.info("updatedPost -->{}", previousPost);

    }
    @Test
    void addCommentToExistingPostTest(){
//        fetch the post from the database

        Post savedPost  = postRepository.findById(41).orElse(null);
        assertThat(savedPost).isNotNull();
//create comments object
        Comment comment1 = new Comment("Billy", "Really Insane!");
        Comment comment2 = new Comment("Amaka", "Loves Java!");

//        save the post
        postRepository.save(savedPost);

        Post commentedPost = postRepository.findById(savedPost.getId()).orElse(null);
        assertThat(commentedPost).isNotNull();
//        assertThat()


    }
    @Test
    @Rollback(value = false)
    void findAllPostInDescendingOrderTest(){

        List<Post> allPosts = postRepository.findByOrderByDatePublishedDesc();
        assertThat(allPosts).isNotEmpty();
        log.info("All Post -->{}",allPosts);
        allPosts.get(0).getDatePublished().isAfter(allPosts.get(1).getDatePublished());
        allPosts.forEach(post ->{
            log.info("Post Date {}",post.getDatePublished());
        });

    }


}