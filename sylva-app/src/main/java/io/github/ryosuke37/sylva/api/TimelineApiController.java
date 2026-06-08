package io.github.ryosuke37.sylva.api;

import io.github.ryosuke37.sylva.common.enums.FetchDirection;
import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.service.PostService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/timeline")
public class TimelineApiController {

    private final PostService postService;

    @Autowired
    TimelineApiController(
            PostService postService
    ) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getTimeline(
            @Validated @NotNull @RequestParam int limit
    ) {
        List<PostDto> timeline = postService.getTimeline(limit, LocalDateTime.now(), FetchDirection.Older);

        return ResponseEntity.status(HttpStatus.OK).body(timeline);
    }

    @GetMapping("/older")
    public ResponseEntity<List<PostDto>> getOlderTimeline(
            @Validated @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime point,
            @Validated @NotNull @RequestParam int limit
    ) {
        List<PostDto> timeline = postService.getTimeline(limit, point, FetchDirection.Older);

        return ResponseEntity.status(HttpStatus.OK).body(timeline);
    }

    @GetMapping("/newer")
    public ResponseEntity<List<PostDto>> getNewerTimeline(
            @Validated @NotNull @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime point,
            @Validated @NotNull @RequestParam int limit
    ) {
        List<PostDto> timeline = postService.getTimeline(limit, point, FetchDirection.Newer);

        return ResponseEntity.status(HttpStatus.OK).body(timeline);
    }
}
