package com.example.demo.post.controller.port;

import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreateDto;
import com.example.demo.post.domain.PostUpdateDto;

public interface PostService {
    Post getById(long id);

    Post create(PostCreateDto postCreateDto);

    Post update(long id, PostUpdateDto postUpdateDto);

}
