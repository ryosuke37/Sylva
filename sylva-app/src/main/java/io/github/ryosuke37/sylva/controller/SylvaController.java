package io.github.ryosuke37.sylva.controller;

import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.controller.dto.PostTreeDto;
import io.github.ryosuke37.sylva.controller.dto.UserDto;
import io.github.ryosuke37.sylva.service.AuthService;
import io.github.ryosuke37.sylva.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SylvaController {

    private final AuthService authService;
    private final PostService postService;

    @Autowired
    SylvaController(
            AuthService authService,
            PostService postService
    ) {
        this.authService = authService;
        this.postService = postService;
    }

    @GetMapping
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView("/top");
        UserDto loginUser = authService.getLoginUser();
        List<PostDto> posts = postService.getLatestPostDtosForTop();
        mav.addObject("loginUser", loginUser);
        mav.addObject("posts", posts);
        mav.addObject("message", "Welcome to Sylva!");
        return mav;
    }

    @GetMapping("/tree/{id}")
    public ModelAndView tree(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("/tree");
        UserDto loginUser = authService.getLoginUser();
        PostTreeDto tree = postService.getPostTree(id);
        mav.addObject("loginUser", loginUser);
        mav.addObject("tree", tree);
        return mav;
    }
}
