package com.ohoon.board.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private String content;

    private String author;

    private Long view;

    private boolean isRemoved;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;
}
