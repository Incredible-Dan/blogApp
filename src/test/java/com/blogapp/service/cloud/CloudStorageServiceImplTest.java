package com.blogapp.service.cloud;

import com.blogapp.web.dto.PostDto;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class CloudinaryCloudStorageServiceImplTest {


    @Autowired @Qualifier("cloudinary")
    CloudStorageService cloudStorageServiceImpl;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadMultiPartFilesImageTest() {
//        define a file
        File file = new File("C:\\Users\\user\\Documents\\Spring.IO\\blogapp\\src\\main\\resources\\static\\asset\\images\\blog-image1.jpg");
        assertThat(file.exists()).isTrue();
        Map<Object, Object> params = new HashMap<>();

        params.put("public_id", "blogapp/first_image");
        params.put("overwrite", true);
        try {
            cloudStorageServiceImpl.uploadImage(file, params);
        } catch (IOException e) {
            log.info("Error Occurred -->{}", e.getMessage());
        }
    }

        @Test
        void uploadMultiPartFilesImageFileTest() throws IOException{

            PostDto postDto = new PostDto();
            postDto.setTitle("Test");
            postDto.setContent("Test");
             Path path = Paths.get("C:\\Users\\user\\Documents\\Spring.IO\\blogapp\\src\\main\\resources\\static\\asset\\images\\author-image1.jpg");
             assertThat(path.toFile().exists());

            MultipartFile multipartFile = new MockMultipartFile("baby","author-image1.jpg","img/jpg",Files.readAllBytes(path));

            log.info("Multipart object created -->{}", multipartFile);

            assertThat(multipartFile).isNotNull();
            assertThat(multipartFile.isEmpty()).isFalse();
            postDto.setCoverImage((multipartFile));

            log.info("File name -->{}", postDto.getCoverImage().getOriginalFilename());

            cloudStorageServiceImpl.uploadImage(multipartFile, ObjectUtils.asMap(
                    "public_id", "blogapp/"+postDto.getCoverImage().getOriginalFilename()
            ));
            assertThat(postDto.getCoverImage().getOriginalFilename()).isEqualTo("author-image1.jpg");



    }
}