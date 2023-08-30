package com.ohoon.board.web;

import com.ohoon.board.app.dto.CurrentMemberDto;
import com.ohoon.board.app.dto.PostListDto;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public String list(
            @CurrentMember CurrentMemberDto currentMember,
            Model model) {
        Page<PostListDto> postListDtoPages = postService.list(PageRequest.of(0, 30));
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("listDtos", postListDtoPages.getContent());
        model.addAttribute("pageNumber", postListDtoPages.getPageable().getPageNumber());
        model.addAttribute("totalPages", postListDtoPages.getTotalPages());
        return "posts/list";
    }
}
