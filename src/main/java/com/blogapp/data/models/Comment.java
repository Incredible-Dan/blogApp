package com.blogapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.type.BinaryType;
import org.hibernate.type.UUIDBinaryType;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;


    private String commenterName;

    @Column(nullable = false, length = 150)
    private String content;

    @CreationTimestamp
    private LocalDateTime datePublished;


    public Comment(String commenterName, String content) {
        this.content = content;
        this.commenterName = commenterName;
    }
}
