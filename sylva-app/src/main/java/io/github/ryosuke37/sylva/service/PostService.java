package io.github.ryosuke37.sylva.service;

import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.mapper.PostMapper;
import io.github.ryosuke37.sylva.repository.PostRepository;
import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static io.github.ryosuke37.sylva.repository.specification.PostSpecification.oderByCreatedDate;
import static io.github.ryosuke37.sylva.repository.specification.PostSpecification.parentPostIdIs;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final PostMapper postMapper;
    private final int POST_PER_PAGE = 10;

    @Autowired
    PostService(
            PostRepository postRepository,
            PostMapper postMapper
    ) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    public List<PostDto> getLatestPostDtosForTop() {
        List<PostEntity> postEntities = postRepository
                .findBy(
                        parentPostIdIs(null),
                        p -> p.page(PageRequest.of(0, POST_PER_PAGE, oderByCreatedDate(Sort.Direction.DESC))))
                .getContent();

        return postMapper.shallowMappingToDtos(postEntities);
    }
}
