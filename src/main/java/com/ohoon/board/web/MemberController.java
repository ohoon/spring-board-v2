package com.ohoon.board.web;

import com.ohoon.board.app.dto.MemberJoinDto;
import com.ohoon.board.app.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinDto", new MemberJoinDto());
        return "members/joinForm";
    }

    @PostMapping("/join")
    public String join(
            @Valid @ModelAttribute MemberJoinDto joinDto,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "members/joinForm";
        }

        memberService.join(joinDto);
        return "redirect:/login";
    }
}
