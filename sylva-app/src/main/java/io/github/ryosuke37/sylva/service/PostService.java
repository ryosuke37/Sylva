package io.github.ryosuke37.sylva.service;

import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.controller.dto.PostTreeDto;
import io.github.ryosuke37.sylva.mapper.PostMapper;
import io.github.ryosuke37.sylva.repository.PostRepository;
import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.github.ryosuke37.sylva.repository.specification.PostSpecification.*;

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
                        fetchUser().and(parentPostIdIs(null)),
                        p -> p.page(PageRequest.of(0, POST_PER_PAGE, oderByCreatedDate(Sort.Direction.DESC))))
                .getContent();

        return postMapper.shallowMappingToDtos(postEntities);
    }

    public PostTreeDto getPostTree(String postId) {
        PostEntity target = postRepository.findById(postId).orElse(null);
        if (target == null) {
            return null;
        }
        List<PostEntity> parents = getAncestorPosts(target);
        List<PostEntity> descendants = getDescendantPosts(target);
        return new PostTreeDto(
                postMapper.shallowMappingToDtos(parents),
                postMapper.shallowMappingToDto(target),
                postMapper.shallowMappingToDtos(descendants)
        );
    }

    private List<PostEntity> getAncestorPosts(PostEntity post) {
        if (post == null || post.getId() == null || !post.hasParent()) {
            return null;
        }

        String rootPostId = post.getRootPost().getId();
        Specification<PostEntity> rootOrSameRootSpec = rootPostIdIs(rootPostId).or(idIs(rootPostId));
        Specification<PostEntity> beforeThisPostSpec = createdDateBetween(null, post.getCreatedDate());
        List<PostEntity> sameRootPost = postRepository.findBy(
                fetchUser().and(rootOrSameRootSpec).and(beforeThisPostSpec),
                p -> p.sortBy(oderByCreatedDate(Sort.Direction.DESC)).all()
        );

        List<PostEntity> ancestorPosts = new ArrayList<>();
        String parentPostId = post.getParentPost().getId();
        for (PostEntity p : sameRootPost) {
            if (p.getId().equals(parentPostId)) {
                ancestorPosts.add(p);
                if(!p.getId().equals(rootPostId)){
                    parentPostId = p.getParentPost().getId();
                }
            }
        }
        Collections.reverse(ancestorPosts);
        return ancestorPosts;
    }

    private List<PostEntity> getDescendantPosts(PostEntity post) {
        if (post == null || post.getId() == null) {
            return null;
        }
        Specification<PostEntity> descendantSpec = parentPostIdIs(post.getId());
        return postRepository.findBy(
                fetchUser().and(descendantSpec),
                p -> p.sortBy(oderByCreatedDate(Sort.Direction.ASC)).all()
        );
    }
}
