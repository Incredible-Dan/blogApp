package com.blogapp.data.models;

import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "blog_post")
@Data
//@Table
public class Post {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)
    private String title;

    @Column(length = 500)
    private String content;

    private String coverImageURL;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn
    private Author author;

    @CreationTimestamp
    private LocalDate datePublished;

    @UpdateTimestamp
    private LocalDate dateModified;

    @OneToMany
    private List <Comment> comments;

}
