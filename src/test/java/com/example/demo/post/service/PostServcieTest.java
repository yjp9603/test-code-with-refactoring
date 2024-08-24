package com.example.demo.post.service;

import com.example.demo.post.domain.PostCreateDto;
import com.example.demo.post.domain.PostUpdateDto;
import com.example.demo.post.infrastructure.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource("classpath:test-application.properties")
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

})
public class PostServcieTest {
    @Autowired
    private PostService postService;

    @Test
    void getById는_존재하는_게시글을_가져온다() {
        //given
        //when
        PostEntity post = postService.getById(1);

        //then
        assertThat(post.getContent()).isEqualTo("SpringTest");
        assertThat(post.getWriter().getEmail()).isEqualTo("yjp9603@gmail.com");
    }

    @Test
    void postCreateDto_를_이용하여_게시물을_생성할_수_있다() {
        // given
        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(1)
                .content("asdasd")
                .build();

        // when
        PostEntity result = postService.create(postCreateDto);

        // then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getContent()).isEqualTo("asdasd");
        assertThat(result.getCreatedAt()).isGreaterThan(0);
    }

    @Test
    void postUpdateDto_를_이용하여_게시물을_수정할_수_있다() {
        // given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder()
                .content("hellohello")
                .build();

        // when
        postService.update(1, postUpdateDto);

        // then
        PostEntity postEntity= postService.getById(1);
        assertThat(postEntity.getContent()).isEqualTo("hellohello");
        assertThat(postEntity.getModifiedAt()).isGreaterThan(0);
    }
}
