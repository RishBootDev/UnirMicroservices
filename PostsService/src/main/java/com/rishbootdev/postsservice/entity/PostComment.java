package com.rishbootdev.postsservice.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "post_comments")
@Getter
@Setter
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long postId;
    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Long parentCommentId;

    private boolean edited = false;
    private boolean deleted = false;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
