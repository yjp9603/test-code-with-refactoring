package com.example.demo.post.service;

import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.post.controller.port.PostService;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreateDto;
import com.example.demo.post.domain.PostUpdateDto;
import com.example.demo.post.service.port.PostRepository;
import com.example.demo.user.domain.User;

import com.example.demo.user.service.port.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ClockHolder clockHolder;

    public Post getById(long id) {
        return postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Posts", id));
    }

    public Post create(PostCreateDto postCreateDto) {
        User writer = userRepository.getById(postCreateDto.getWriterId());
        Post post = Post.from(writer, postCreateDto, clockHolder);
        return postRepository.save(post);

    }

    public Post update(long id, PostUpdateDto postUpdateDto) {
        Post post = getById(id);
        post = post.update(postUpdateDto, clockHolder);
        post = postRepository.save(post);
        return post;
    }
}