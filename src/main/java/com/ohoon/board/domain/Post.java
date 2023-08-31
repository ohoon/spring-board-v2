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
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    @Size(max=64)
    private String title;

    @NotNull
    @Column(columnDefinition = "TEXT")
    private String content;

    @NotNull
    @Size(max=20)
    private String author;

    private long view;

    private boolean isRemoved;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.REMOVE
    )
    private final List<Comment> comments = new ArrayList<>();

    private Post(String title, String content, String author, Member member) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.member = member;
    }

    public static Post create(String title, String content, Member member) {
        return new Post(title, content, member.getName(), member);
    }

    public Long getMemberId() {
        return member.getId();
    }

    public void edit(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void remove() {
        this.isRemoved = true;
    }

    public int getTotalComments() {
        return this.comments.size();
    }

    public void increaseView() {
        this.view++;
    }
}
