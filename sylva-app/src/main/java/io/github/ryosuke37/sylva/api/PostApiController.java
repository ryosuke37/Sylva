package io.github.ryosuke37.sylva.api;

import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.controller.form.PostForm;
import io.github.ryosuke37.sylva.service.AuthService;
import io.github.ryosuke37.sylva.service.PostService;
import io.github.ryosuke37.sylva.service.exception.PostNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("api/post")
public class PostApiController {

    private final AuthService authService;
    private final PostService postService;

    @Autowired
    PostApiController(
            AuthService authService,
            PostService postService
    ) {
        this.authService = authService;
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostDto> post(
            @Validated @RequestBody PostForm postForm,
            UriComponentsBuilder uriComponentsBuilder
    ) throws PostNotFoundException {
        PostDto createdPost = postService.savePost(postForm, authService.getLoginUser().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }
}
