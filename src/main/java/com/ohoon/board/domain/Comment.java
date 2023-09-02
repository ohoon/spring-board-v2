package com.ohoon.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @NotNull
    @Size(max=255)
    private String content;

    @NotNull
    @Size(max=20)
    private String author;

    private boolean isRemoved;

    @OneToMany(mappedBy = "parent")
    private final List<Comment> children = new ArrayList<>();

    private Comment(String content, String author, Member member, Post post) {
        this.content = content;
        this.author = author;
        this.member = member;
        this.post = post;
    }

    public static Comment create(String content, Member member, Post post) {
        return new Comment(content, member.getName(), member, post);
    }

    public Long getMemberId() {
        return member.getId();
    }

    public Long getPostId() {
        return post.getId();
    }

    public void remove() {
        this.isRemoved = true;
    }

    public void assignParent(Comment comment) {
        this.parent = comment;
        comment.getChildren().add(this);
    }
}
