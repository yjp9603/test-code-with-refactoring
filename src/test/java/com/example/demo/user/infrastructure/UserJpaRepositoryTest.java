package com.example.demo.user.infrastructure;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

@DataJpaTest
@Sql("/sql/user-repository-test-data.sql")
public class UserJpaRepositoryTest {


    @Autowired
    private UserJpaRepository userJpaRepository;

    @Test
    void findByIdAndStatus로_유저데이터를_찾아오기() {
        //given
        // when
        Optional<UserEntity> user = userJpaRepository.findByIdAndStatus(1, UserStatus.ACTIVE);

        // then
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    void findByIdAndStatus는_데이터가_없으면_Optional_empty_를_리턴한다() {
        //given
        // when
        Optional<UserEntity> result = userJpaRepository.findByIdAndStatus(1, UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }

    @Test
    void findByEmailAndStatus로_유저데이터를_찾아온다() {
        //given
        // when
        Optional<UserEntity> user = userJpaRepository.findByEmailAndStatus("yjp9603@gmail.com", UserStatus.ACTIVE);

        // then
        assertThat(user.isPresent()).isTrue();
    }

    @Test
    void findByEmailAndStatus는_데이터가_없으면_Optional_empty_를_반환한다() {
        //given

        // when
        Optional<UserEntity> result = userJpaRepository.findByEmailAndStatus("yjp9603@gmail.com", UserStatus.PENDING);

        // then
        assertThat(result.isEmpty()).isTrue();
    }


}
