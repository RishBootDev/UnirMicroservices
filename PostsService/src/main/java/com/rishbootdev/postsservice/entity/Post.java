package com.rishbootdev.postsservice.entity;

import com.rishbootdev.postsservice.enums.PostType;
import com.rishbootdev.postsservice.enums.PostVisibility;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "posts",
        indexes = {
                @Index(name = "idx_post_user", columnList = "userId"),
                @Index(name = "idx_post_created", columnList = "createdAt")
        }
)
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String mediaUrl;

    private Long originalPostId;

    @Enumerated(EnumType.STRING)
    private PostType type = PostType.NORMAL;

    @Enumerated(EnumType.STRING)
    private PostVisibility visibility = PostVisibility.PUBLIC;

    private boolean edited = false;
    private boolean deleted = false;

    // denormalized counters
    private long likeCount = 0;
    private long commentCount = 0;
    private long repostCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
