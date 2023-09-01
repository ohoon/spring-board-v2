package com.ohoon.board.web;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public String list(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        Page<PostListDto> postListDtoPages = postService.list(PageRequest.of(0, 30));
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("listDtos", postListDtoPages.getContent());
        model.addAttribute("pageNumber", postListDtoPages.getPageable().getPageNumber());
        model.addAttribute("totalPages", postListDtoPages.getTotalPages());
        return "posts/list";
    }

    @GetMapping("/write")
    public String writeForm(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("writeDto", new PostWriteDto());
        return "posts/writeForm";
    }

    @PostMapping("/write")
    public String write(
            @CurrentMember CurrentMemberDto currentMember,
            @Valid @ModelAttribute("writeDto") PostWriteDto writeDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "posts/writeForm";
        }

        postService.write(currentMember.getMemberId(), writeDto);
        return "redirect:/post";
    }

    @GetMapping("/{id}")
    public String read(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            Model model
    ) {
        PostReadDto postReadDto = postService.read(postId);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("readDto", postReadDto);
        return "posts/read";
    }

    @PreAuthorize("@postService.isAuthor(#postId, #currentMember.memberId)")
    @GetMapping("/{id}/edit")
    public String editForm(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            Model model
    ) {
        PostReadDto postReadDto = postService.read(postId);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("editDto", PostEditDto.fromReadDto(postReadDto));
        return "posts/editForm";
    }

    @PreAuthorize("@postService.isAuthor(#postId, #currentMember.memberId)")
    @PostMapping("/{id}/edit")
    public String edit(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @Valid @ModelAttribute("editDto") PostEditDto editDto,
            BindingResult result,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            return "posts/editForm";
        }

        postService.edit(postId, editDto);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }

    @PreAuthorize("@postService.isAuthor(#postId, #currentMember.memberId)")
    @PostMapping("/{id}/remove")
    public String remove(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId
    ) {
        postService.remove(postId);
        return "redirect:/post";
    }
}
