package com.ohoon.board.web;

import com.ohoon.board.app.dto.AuthPasswordLoginDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new AuthPasswordLoginDto());
        return "auths/loginForm";
    }
}
