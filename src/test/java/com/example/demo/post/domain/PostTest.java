package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostTest {
    @Test
    public void Post는_PostCreateDto_객체로_생성할_수_있다() {
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
        Post post = Post.from(writer, postCreateDto);

        //then
        assertThat(post.getContent()).isEqualTo("hello test");
        assertThat(post.getWriter().getEmail()).isEqualTo("yjp9603@gmail.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("jaden");
        assertThat(post.getWriter().getAddress()).isEqualTo("Seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
    }

    @Test
    public void Post는_PostUpdateDto_객체로_수정할_수_있다() {

    }
}
