package com.ohoon.board.web;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.MemberService;
import com.ohoon.board.app.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManageController {

    private final MemberService memberService;

    @GetMapping("/member")
    public String manageMember(
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
}
