package com.blogapp.data.models;

import lombok.Data;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name="blog_post")
@Data
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // we set the id ourselves
    private Integer id;

    @Column(nullable = false, length = 50, unique = true)  // nullable set to false means it can't be null
    private String title;

    @Column(length = 500) // the default length is 250
    private String content;

    private String coverImage;

    @ManyToOne(cascade = CascadeType.PERSIST) //the first is for the class that you're currently in. 1 author to many blog posts
    @JoinColumn
    private Author author;

    @CreationTimestamp // database automatically stamps when the PostService was created
    private LocalDateTime datePublished;

    @UpdateTimestamp // database automatically stamps when the PostService was updated
    private LocalDateTime dateModified;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Comment> comments; //whenever the relationship you're extending to, it has to be in a list


    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        this.comments.addAll(Arrays.asList(comment));
    }
}
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("Post{");
//        sb.append("id=").append(id);
//        sb.append(", title='").append(title).append('\'');
//        sb.append(", content='").append(content).append('\'');
//        sb.append(", coverImageURL='").append(coverImage).append('\'');
//        sb.append(", author=").append(author);
//        sb.append(", datePublished=").append(datePublished);
//        sb.append(", dateModified=").append(dateModified);
//        sb.append(", comments=").append(comments);
//        sb.append('}');
//        return sb.toString();
//    }
//}
