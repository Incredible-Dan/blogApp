package com.blogapp.web.contoller;

import com.blogapp.data.models.Post;
import com.blogapp.service.post.PostService;
import com.blogapp.web.dto.PostDto;
import com.blogapp.web.exceptions.PostDoesNotExistException;
import com.blogapp.web.exceptions.PostObjectIsNullException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.thymeleaf.model.IModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postServiceImpl;


    @GetMapping("")
    public String  getIndex(Model model) {
        model.addAttribute("postList", postServiceImpl.findAllPosts());
        return "index";//Spring boot will look for an html page that matches this name in template folder

    }

    @GetMapping(value = "/info/{post_id}")
    public String getPostDetail(@PathVariable("post_id") Integer post_id, Model model) throws PostDoesNotExistException {
        log.info("Request for a post path -->{}", post_id);
        Post savedPost = postServiceImpl.findPostById(post_id);
        model.addAttribute("postInfo", savedPost);
        return "post";
    }

    @GetMapping("/create")
    public String getPostForm(Model model) {
    /*
        We're passing a model to this page due to the http method which is a getMapping
         */

        model.addAttribute("error", false);
        return "create";
    }

    @PostMapping("/save")
    public String savePost(@ModelAttribute @Valid PostDto postDto, BindingResult result, Model model) {
        /*the @valid is to enforce our valid constraint
            the @ModelAttribute is used during MVC
         */
        log.info("Post dto received -->{}", postDto);

        if (result.hasErrors()) {
            return "create";
        }
        try {
            postServiceImpl.savePost(postDto);
        } catch (PostObjectIsNullException pe) {
            log.info("Exception Occurred -->{}", pe.getMessage());
        } catch (DataIntegrityViolationException dx) {
            log.info("Constraint exception occurred -->{}", dx.getMessage());
            model.addAttribute("error", true);
            model.addAttribute("errorMessage", dx.getMessage());
//            model.addAttribute("postDto", new PostDto());
            return "create";
        }
        return "redirect:/posts";
    }

    @ModelAttribute
    public void createPostModel(Model model) {
        model.addAttribute("post", new PostDto());
    }
}

//    @GetMapping("/info/{postid}")
//    public String getPostDetails(@PathVariable("postId") Integer postId, Model model){
//        log.info("Request for a post path -->{}",postId);
//        try{
//            Post savedPost = new postServiceImpl.findById(postId);
//            model.addAttribute("postInfo", savedPost);
//        }catch(PostDoesNotExistsException exception){
//            log.info("Exception occurred");
//        }
//
//        return "post";
//    }
//}

