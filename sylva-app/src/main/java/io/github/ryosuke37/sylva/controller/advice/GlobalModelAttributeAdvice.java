package io.github.ryosuke37.sylva.controller.advice;

import io.github.ryosuke37.sylva.controller.dto.UserDto;
import io.github.ryosuke37.sylva.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    private final AuthService authService;

    @Autowired
    GlobalModelAttributeAdvice(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @ModelAttribute
    public void addCommonAttributes(Model model) {
        UserDto loginUser = authService.getLoginUser();
        model.addAttribute("loginUser", loginUser);
    }
}
