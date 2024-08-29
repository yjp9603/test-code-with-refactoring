package com.example.demo.post.controller.response;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import com.example.demo.post.domain.Post;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PostResponseTest {

    @Test
    public void Post로_응답을_생성할_수_있다() {
        // given
        Post post = Post.builder()
                .content("hello test")
                .writer(User.builder()
                        .email("yjp9603@gmail.com")
                        .nickname("jaden")
                        .address("Seoul")
                        .status(UserStatus.ACTIVE)
                        .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab")
                        .build())
                .build();

        // when
        PostResponse postResponse = PostResponse.from(post);

        // then
        assertThat(postResponse.getContent()).isEqualTo("hello test");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("yjp9603@gmail.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("jaden");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
    }
}
