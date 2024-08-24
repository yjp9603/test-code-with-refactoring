package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.post.domain.PostCreateDto;
import com.example.demo.post.domain.PostUpdateDto;
import com.example.demo.post.infrastructure.PostEntity;
import com.example.demo.post.infrastructure.PostRepository;
import com.example.demo.user.infrastructure.UserEntity;
import java.time.Clock;

import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostEntity getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public PostEntity create(PostCreateDto postCreateDto) {
        UserEntity user = userService.getById(postCreateDto.getWriterId());
        PostEntity post = new PostEntity();
        post.setWriter(user);
        post.setContent(postCreateDto.getContent());
        post.setCreatedAt(Clock.systemUTC().millis());
        return postRepository.save(post);
    }

    public PostEntity update(long id, PostUpdateDto postUpdateDto) {
        PostEntity post = getById(id);
        post.setContent(postUpdateDto.getContent());
        post.setModifiedAt(Clock.systemUTC().millis());
        return postRepository.save(post);
    }
}