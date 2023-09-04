package com.ohoon.board.web;

import com.ohoon.board.app.dto.CommentLeafDto;
import com.ohoon.board.app.dto.CurrentMemberDto;
import com.ohoon.board.app.dto.PostListDto;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.CommentService;
import com.ohoon.board.app.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class HomeController {

    private final PostService postService;

    private final CommentService commentService;

    @GetMapping("")
    public String home(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        List<PostListDto> recentPostListDtos = postService.recentList();
        List<CommentLeafDto> recentCommentLeafDtos = commentService.recentList();
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("postListDtos", recentPostListDtos);
        model.addAttribute("commentLeafDtos", recentCommentLeafDtos);
        return "home";
    }
}
