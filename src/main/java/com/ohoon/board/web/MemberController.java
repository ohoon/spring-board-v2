package com.ohoon.board.web;

import com.ohoon.board.app.dto.*;
import com.ohoon.board.app.security.CurrentMember;
import com.ohoon.board.app.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("")
    public String profile(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        MemberProfileDto memberProfileDto = memberService.findById(currentMember.getMemberId());
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("profileDto", memberProfileDto);
        return "members/profile";
    }

    @GetMapping("/join")
    public String joinForm(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("joinDto", new MemberJoinDto());
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String join(
            @Valid @ModelAttribute(name = "joinDto") MemberJoinDto joinDto,
            BindingResult result
    ) {
        validateDuplicateUsername(joinDto.getUsername(), result);
        if (result.hasErrors()) {
            return "members/joinForm";
        }

        memberService.join(joinDto);
        return "redirect:/login";
    }

    private void validateDuplicateUsername(String username, BindingResult result) {
        if (!memberService.isUniqueUsername(username)) {
            result.addError(new FieldError(
                    "joinDto",
                    "username",
                    "이미 등록된 아이디입니다."));
        }
    }

    @GetMapping("/modify")
    public String modifyForm(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        MemberProfileDto memberProfileDto = memberService.findById(currentMember.getMemberId());
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("modifyDto", MemberModifyDto.fromProfileDto(memberProfileDto));
        return "members/modifyForm";
    }

    @PostMapping("/modify")
    public String modify(
            @CurrentMember CurrentMemberDto currentMember,
            @Valid @ModelAttribute("modifyDto") MemberModifyDto modifyDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "members/modifyForm";
        }

        memberService.modify(currentMember.getMemberId(), modifyDto);
        return "redirect:/logout";
    }

    @PostMapping("/quit")
    public String quit(
            @CurrentMember CurrentMemberDto currentMember
    ) {
        memberService.quit(currentMember.getMemberId());
        return "redirect:/logout";
    }

    @GetMapping("/changePassword")
    public String changePasswordForm(
            @CurrentMember CurrentMemberDto currentMember,
            Model model
    ) {
        model.addAttribute("currentMember", currentMember);
        model.addAttribute("changePasswordDto", new MemberChangePasswordDto());
        return "members/changePasswordForm";
    }

    @PostMapping("/changePassword")
    public String changePassword(
            @CurrentMember CurrentMemberDto currentMember,
            @Valid @ModelAttribute("changePasswordDto") MemberChangePasswordDto changePasswordDto,
            BindingResult result
    ) {
        validateOldPassword(currentMember.getMemberId(), changePasswordDto.getOldPassword(), result);
        if (result.hasErrors()) {
            return "members/changePasswordForm";
        }

        memberService.changePassword(currentMember.getMemberId(), changePasswordDto.getNewPassword());
        return "redirect:/logout";
    }

    private void validateOldPassword(Long memberId, String rawPassword, BindingResult result) {
        if (!memberService.matchesPassword(memberId, rawPassword)) {
            result.addError(new FieldError(
                    "changePasswordDto",
                    "oldPassword",
                    "현재 비밀번호가 일치하지 않습니다."));
        }
    }
}
