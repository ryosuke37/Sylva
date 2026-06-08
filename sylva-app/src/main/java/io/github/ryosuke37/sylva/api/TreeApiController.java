package io.github.ryosuke37.sylva.api;

import io.github.ryosuke37.sylva.controller.dto.PostTreeDto;
import io.github.ryosuke37.sylva.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/tree")
public class TreeApiController {

    private final PostService postService;

    @Autowired
    TreeApiController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostTreeDto> getTree(
            @PathVariable String id
    ) {
        PostTreeDto tree = postService.getPostTree(id);

        return ResponseEntity.status(HttpStatus.OK).body(tree);
    }
}
