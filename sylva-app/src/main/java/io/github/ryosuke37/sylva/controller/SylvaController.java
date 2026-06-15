package io.github.ryosuke37.sylva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SylvaController {

    @GetMapping
    public ModelAndView top() {
        return new ModelAndView("/top");
    }

    @GetMapping("/tree/{id}")
    public ModelAndView tree(@PathVariable String id) {
        ModelAndView mav = new ModelAndView("/tree");
        mav.addObject("postId", id);
        return mav;
    }
}
