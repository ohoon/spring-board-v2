package com.ohoon.board.app.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchCondition {

    @Enumerated(EnumType.STRING)
    private PostSearchMode mode = PostSearchMode.TITLE_CONTENT;

    private String keyword;
}
