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

    private boolean isNotice;

    private boolean isRemoved;

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.REMOVE
    )
    private final List<Comment> comments = new ArrayList<>();

    @OneToMany(
            mappedBy = "post",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private final List<Vote> votes = new ArrayList<>();

    private Post(String title, String content, String author, boolean isNotice, Member member) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.isNotice = isNotice;
        this.member = member;
    }

    public static Post create(String title, String content, boolean isNotice, Member member) {
        return new Post(title, content, member.getName(), isNotice, member);
    }

    public Long getMemberId() {
        return member.getId();
    }

    public void edit(String title, String content, boolean isNotice) {
        this.title = title;
        this.content = content;
        this.isNotice = isNotice;
    }

    public void remove() {
        this.isRemoved = true;
    }

    public long getTotalComments() {
        return this.comments.stream()
                .filter(comment -> !comment.isRemoved())
                .count();
    }

    public void increaseView() {
        this.view++;
    }

    public void addVote(Vote vote) {
        this.votes.add(vote);
    }

    public boolean isVotedBy(Long memberId) {
        return this.votes.stream()
                .anyMatch(vote -> vote.getMemberId().equals(memberId));
    }

    public long getTotalVotes() {
        return this.votes.size();
    }
}
