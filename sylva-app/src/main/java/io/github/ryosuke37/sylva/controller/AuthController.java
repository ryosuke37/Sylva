package io.github.ryosuke37.sylva.controller;

import io.github.ryosuke37.sylva.common.constant.Routes;
import io.github.ryosuke37.sylva.controller.form.LoginForm;
import io.github.ryosuke37.sylva.controller.form.SignupForm;
import io.github.ryosuke37.sylva.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AuthController {

    private final AuthService authService;

    @Autowired
    AuthController(
            AuthService authService
    ) {
        this.authService = authService;
    }

    @GetMapping(Routes.LOGIN)
    public ModelAndView getLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("loginForm", new LoginForm());
        return mav;
    }

    @GetMapping(Routes.SIGNUP)
    public ModelAndView getSignUp() {
        ModelAndView mav = new ModelAndView("signup");
        mav.addObject("signupForm", new SignupForm());
        return mav;
    }

    @PostMapping(Routes.SIGNUP)
    public ModelAndView postSignUp(
            @ModelAttribute("signupForm")
            @Validated
            SignupForm signUpForm,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("signup");
        }
        authService.signUp(signUpForm);
        return new ModelAndView("redirect:/");
    }

    @GetMapping(Routes.LOGOUT)
    public ModelAndView getLogout(){
        return  new ModelAndView("logout");
    }
}
