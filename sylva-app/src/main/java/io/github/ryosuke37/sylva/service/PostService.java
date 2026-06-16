package io.github.ryosuke37.sylva.service;

import io.github.ryosuke37.sylva.common.enums.FetchDirection;
import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.controller.dto.PostTreeDto;
import io.github.ryosuke37.sylva.controller.form.PostForm;
import io.github.ryosuke37.sylva.mapper.PostMapper;
import io.github.ryosuke37.sylva.repository.PostRepository;
import io.github.ryosuke37.sylva.repository.UserRepository;
import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import io.github.ryosuke37.sylva.repository.entity.UserEntity;
import io.github.ryosuke37.sylva.service.exception.PostNotFoundException;
import io.github.ryosuke37.sylva.service.exception.UserNotFoundException;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.github.ryosuke37.sylva.repository.specification.PostSpecification.*;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    @Autowired
    PostService(
            PostRepository postRepository,
            UserRepository userRepository,
            PostMapper postMapper
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    public PostDto getPost(@Nonnull String postId)
            throws PostNotFoundException{
        PostEntity postEntity = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not exists."));
        return postMapper.shallowMappingToDto(postEntity);
    }

    public PostDto savePost(@Nonnull PostForm postForm, @Nonnull String userId)
            throws PostNotFoundException, UserNotFoundException {
        String parentPostId = postForm.getParentPostId();
        String quotedPostId = postForm.getQuotedPostId();
        PostEntity parentPost = null;
        PostEntity rootPost = null;
        PostEntity quotedPost = null;

        if (!parentPostId.isBlank()) {
            parentPost = postRepository.findById(parentPostId)
                    .orElseThrow(() -> new PostNotFoundException("No post designated as the parent post exists."));
            rootPost = parentPost.getRootPost() == null
                    ? parentPost
                    : postRepository.findById(parentPost.getRootPost().getId())
                    .orElseThrow(() -> new PostNotFoundException("Root post not exists."));
        }

        if (!quotedPostId.isBlank()) {
            quotedPost = postRepository.findById(postForm.getQuotedPostId())
                    .orElseThrow(() -> new PostNotFoundException("No post designated as the quoted post exists."));
        }

        PostEntity newPost = new PostEntity();

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not exists."));

        newPost.setUser(user);
        newPost.setContent(postForm.getContent());
        newPost.setRootPost(rootPost);
        newPost.setParentPost(parentPost);
        newPost.setQuotedPost(quotedPost);

        PostEntity createdPost = postRepository.save(newPost);
        return postMapper.shallowMappingToDto(createdPost);
    }

    public List<PostDto> getTimeline(int limit, LocalDateTime point, FetchDirection direction) {
        int MAX_NUM_RETURN_POST = 100;
        int clippedLimit = Math.max(0, Math.min(limit, MAX_NUM_RETURN_POST));
        List<PostEntity> postEntities;

        if (direction == FetchDirection.Newer) {
            postEntities = postRepository
                    .findBy(
                            fetchUser()
                                    .and(parentPostIdIs(null))
                                    .and(createdDateBetween(point.plusNanos(1), null)),
                            p -> p
                                    .sortBy(oderByCreatedDate(Sort.Direction.ASC))
                                    .limit(clippedLimit)
                                    .all()
                    );
            Collections.reverse(postEntities);
        } else {
            postEntities = postRepository
                    .findBy(
                            fetchUser()
                                    .and(parentPostIdIs(null))
                                    .and(createdDateBetween(null, point.minusNanos(1))),
                            p -> p
                                    .sortBy(oderByCreatedDate(Sort.Direction.DESC))
                                    .limit(clippedLimit)
                                    .all()
                    );
        }

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
                parents == null ? Collections.emptyList() : postMapper.shallowMappingToDtos(parents),
                postMapper.shallowMappingToDto(target),
                descendants == null ? Collections.emptyList() : postMapper.shallowMappingToDtos(descendants)
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
                if (!p.getId().equals(rootPostId)) {
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
