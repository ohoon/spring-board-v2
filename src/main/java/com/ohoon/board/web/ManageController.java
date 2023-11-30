package com.ohoon.board.web;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.MemberService;
import com.ohoon.board.app.util.Mapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final MemberService memberService;

    private final Mapper mapper;

    @GetMapping("/member")
    public String memberList(
            @CurrentMember CurrentMemberDto currentMember,
            @ModelAttribute("condition") MemberSearchCondition condition,
            @PageableDefault(50) Pageable pageable,
            Model model
    ) {
        Page<MemberListDto> memberListDtoPages = memberService.list(condition, pageable);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("listDtos", memberListDtoPages.getContent());
        model.addAttribute("pageNumber", memberListDtoPages.getNumber());
        model.addAttribute("totalPages", memberListDtoPages.getTotalPages());
        return "manages/memberList";
    }

    @GetMapping("/member/{id}")
    public String memberModifyForm(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long memberId,
            Model model
    ) {
        MemberProfileDto memberProfileDto = memberService.findById(memberId);
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("modifyDto", mapper.toMemberModifyDto(memberProfileDto));
        return "manages/memberModifyForm";
    }

    @PostMapping("/member/{id}")
    public String memberModify(
            @CurrentMember CurrentMemberDto currentMember,
            @PathVariable("id") Long memberId,
            @Valid @ModelAttribute("modifyDto") MemberModifyDto modifyDto,
            BindingResult result,
            Model model
    ) {
        if (result.hasErrors()) {
            model.addAttribute("currentMember", currentMember);
            return "members/modifyForm";
        }

        memberService.modify(memberId, modifyDto);
        return "redirect:/manage/member";
    }
}
