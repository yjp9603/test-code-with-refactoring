package com.example.demo.post.domain;

import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostTest {
    @Test
    public void PostCreateDto로_게시물을_생성할_수_있다() {
        //given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("hello test")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("yjp9603@gmail.com")
                .nickname("jaden")
                .address("Seoul")
                .status(UserStatus.ACTIVE)
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                .build();
        //when
        Post post = Post.from(writer, postCreateDto, new TestClockHolder(1678530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("hello test");
        assertThat(post.getCreatedAt()).isEqualTo(1678530673958L);
        assertThat(post.getWriter().getEmail()).isEqualTo("yjp9603@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("jaden");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }

    @Test
    public void PostUpdateDto로_게시글을_수정할_수_있다() {
        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("hello")
                .build();

        User writer = User.builder()
                .id(1L)
                .email("yjp9603@gmail.com")
                .nickname("jaden")
                .address("Seoul")
                .certificationCode("aaaaaaaaa-aaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .lastLoginAt(0L)
                .build();
        Post post = Post.builder()
            .id(1L)
            .content("spring test")
            .createdAt(1678530673958L)
            .modifiedAt(0L)
            .writer(writer)
            .build();

        //when
        post = post.update(postUpdateDto, new TestClockHolder(1678530673958L));

        //then
        assertThat(post.getContent()).isEqualTo("hello");
        assertThat(post.getModifiedAt()).isEqualTo(1678530673958L);
    }
}
