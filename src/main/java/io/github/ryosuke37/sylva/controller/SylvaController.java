package io.github.ryosuke37.sylva.controller;

import io.github.ryosuke37.sylva.controller.dto.UserDto;
import io.github.ryosuke37.sylva.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SylvaController {

    private final AuthService authService;

    @Autowired
    SylvaController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView("/top");
        UserDto loginUser = authService.getLoginUser();
        mav.addObject("loginUser", loginUser);
        mav.addObject("message", "Welcome to Sylva!");
        return mav;
    }
}
