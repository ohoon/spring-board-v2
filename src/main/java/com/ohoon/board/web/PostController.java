package com.ohoon.board.web;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.CommentService;
import com.ohoon.board.app.service.PostService;
import com.ohoon.board.app.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    private final CommentService commentService;

    private final Mapper mapper;

    @GetMapping("")
    public String list(
            @CurrentMember CurrentMemberDto currentMember,
            @RequestParam(required = false) String tab,
            @ModelAttribute("condition") PostSearchCondition condition,
            @PageableDefault(30) Pageable pageable,
            Model model
    ) {
        List<PostListDto> noticePostListDtos = postService.noticeList();
        Page<PostListDto> postListDtoPages = postService.list(condition, pageable, tab);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("tab", tab);
        model.addAttribute("noticeListDtos", noticePostListDtos);
        model.addAttribute("listDtos", postListDtoPages.getContent());
        model.addAttribute("pageNumber", postListDtoPages.getNumber());
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
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            return "posts/writeForm";
        }

        postService.write(currentMember.getMemberId(), writeDto);
        return "redirect:/post";
    }

    @PreAuthorize("!@postService.isRemoved(#postId)")
    @GetMapping("/{id}")
    public String read(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @RequestParam(required = false) String tab,
            @ModelAttribute("condition") PostSearchCondition condition,
            @PageableDefault(30) @Qualifier Pageable pageable,
            @PageableDefault(30) @Qualifier("comment") Pageable commentPageable,
            Model model
    ) {
        PostReadDto postReadDto = postService.read(postId);
        Page<CommentDto> commentDtoPages = commentService.listByPostId(postId, commentPageable);
        long totalComments = commentService.count(postId);
        List<PostListDto> noticePostListDtos = postService.noticeList();
        Page<PostListDto> postListDtoPages = postService.list(condition, pageable, tab);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("readDto", postReadDto);
        model.addAttribute("commentWriteDto", new CommentWriteDto());
        model.addAttribute("commentDtos", commentDtoPages.getContent());
        model.addAttribute("commentPageNumber", commentDtoPages.getNumber());
        model.addAttribute("commentTotalPages", commentDtoPages.getTotalPages());
        model.addAttribute("totalComments", totalComments);
        model.addAttribute("tab", tab);
        model.addAttribute("noticeListDtos", noticePostListDtos);
        model.addAttribute("listDtos", postListDtoPages.getContent());
        model.addAttribute("pageNumber", postListDtoPages.getNumber());
        model.addAttribute("totalPages", postListDtoPages.getTotalPages());
        return "posts/read";
    }

    @PreAuthorize("hasRole('ADMIN') || @postService.isAuthor(#postId, #currentMember.memberId)")
    @GetMapping("/{id}/edit")
    public String editForm(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            Model model
    ) {
        PostReadDto postReadDto = postService.read(postId);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("editDto", mapper.toPostEditDto(postReadDto));
        return "posts/editForm";
    }

    @PreAuthorize("hasRole('ADMIN') || @postService.isAuthor(#postId, #currentMember.memberId)")
    @PostMapping("/{id}/edit")
    public String edit(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @Valid @ModelAttribute("editDto") PostEditDto editDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            return "posts/editForm";
        }

        postService.edit(postId, editDto);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }

    @PreAuthorize("hasRole('ADMIN') || @postService.isAuthor(#postId, #currentMember.memberId)")
    @PostMapping("/{id}/remove")
    public String remove(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId
    ) {
        postService.remove(postId);
        return "redirect:/post";
    }

    @PostMapping("/{id}/vote")
    public String vote(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            RedirectAttributes redirectAttributes
    ) {
        postService.vote(currentMember.getMemberId(), postId);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }

    @PostMapping("/{id}/comment")
    public String writeComment(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @Valid @ModelAttribute("writeDto") CommentWriteDto writeDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addAttribute("id", postId);
        if (result.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            return "redirect:/post/{id}";
        }

        commentService.write(currentMember.getMemberId(), postId, writeDto);
        return "redirect:/post/{id}";
    }

    @PostMapping("/{id}/comment/{cid}")
    public String writeReply(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @PathVariable("cid") Long commentId,
            @Valid @ModelAttribute("writeDto") CommentWriteDto writeDto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        redirectAttributes.addAttribute("id", postId);
        if (result.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            return "redirect:/post/{id}";
        }

        commentService.writeReply(currentMember.getMemberId(), postId, commentId, writeDto);
        return "redirect:/post/{id}";
    }

    @PreAuthorize("hasRole('ADMIN') || @commentService.isAuthor(#commentId, #currentMember.memberId)")
    @PostMapping("/{id}/comment/{cid}/remove")
    public String removeComment(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long postId,
            @PathVariable("cid") Long commentId,
            RedirectAttributes redirectAttributes
    ) {
        commentService.remove(commentId);
        redirectAttributes.addAttribute("id", postId);
        return "redirect:/post/{id}";
    }
}
