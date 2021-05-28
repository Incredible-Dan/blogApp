package com.blogapp.data.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Comment {

    @Id
//    @GeneratedValue(strategy = GenerationTyp)
    private Integer id;

    private String commenterName;

    @Column(nullable = false, length = 150)
    private String content;

    private LocalDate datePublished;
}
