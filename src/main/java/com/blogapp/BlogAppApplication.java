package com.blogapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@RestController
@SpringBootApplication
public class BlogAppApplication {
//	@RequestMapping("/")
//	String post(){
//		return "Hello World! My first Post!";
//	}

	public static void main(String[] args)
	{
		SpringApplication.run(BlogAppApplication.class, args);
	}

}
