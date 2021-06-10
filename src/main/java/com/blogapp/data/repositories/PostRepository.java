package com.blogapp.data.repositories;

import com.blogapp.data.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*Our repo has to be an interface that extends jpa repository. Note, we have to
pass the entity and the id type. this way hibernate can generate sql
scripts for whatever CRUD operation we wanna perform
 */

@Repository
//@Transactional
public interface PostRepository extends JpaRepository<Post, Integer> {

    Post findByTitle(String title);

    @Query("select p from Post as p where p.title = ?1")
    Post findByPostTitle(@Param("title") String title);

    List<Post> findByOrderByDatePublishedDesc();

}
