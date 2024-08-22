package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.dto.PostCreateDto;
import com.example.demo.model.dto.PostUpdateDto;
import com.example.demo.repository.PostEntity;
import com.example.demo.repository.PostRepository;
import com.example.demo.repository.UserEntity;
import java.time.Clock;
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