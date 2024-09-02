package com.example.demo.user.service;


import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.mock.FakeMailSender;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreateDto;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class UserServiceTest {
    private UserServiceImpl userService;

    @BeforeEach
    void  init() {
        FakeMailSender fakeMailSender = new FakeMailSender();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();
        this.userService = UserServiceImpl.builder()
                .uuidHolder(new TestUuidHolder("aaaaaaaaa-aaaaaaaaa"))
                .clockHolder(new TestClockHolder(1678530673958L))
                .userRepository(fakeUserRepository)
                .certificationService(new CertificationService(fakeMailSender))
                .build();
        fakeUserRepository.save(User.builder()
            .id(1L)
            .email("yjp9603@gmail.com")
            .nickname("jaden")
            .address("Seoul")
            .certificationCode("aaaaaaaaa-aaaaaaaaa")
            .status(UserStatus.ACTIVE)
            .lastLoginAt(0L)
            .build());
        fakeUserRepository.save(User.builder()
                .id(2L)
                .email("yjp9604@gmail.com")
                .nickname("jaden2")
                .address("Ilsan")
                .certificationCode("aaaaaaaaa-aaaaaaaaa")
                .status(UserStatus.PENDING)
                .lastLoginAt(0L)
                .build());
    }

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "yjp9603@gmail.com";

        //when
        User user = userService.getByEmail(email);

        //then
        assertThat(user.getNickname()).isEqualTo("jaden");
    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given
        String email = "yjp9604@gmail.com";

        //when
        //then
        assertThatThrownBy(() -> {
            User user = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void getById는_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        //when
        User user = userService.getById(1);
        //then
        assertThat(user.getNickname()).isEqualTo("jaden");
    }

    @Test
    void getById는_PENDING_상태인_유저는_찾아올_수_없다() {
        //then
        assertThatThrownBy(() -> {
            User user = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void userCreateDto를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreateDto userCreateDto = UserCreateDto.builder()
                .email("yjp9603@gmail.com")
                .address("seoul")
                .nickname("jaden")
                .build();

        //when
        User user = userService.create(userCreateDto);

        //then
        assertThat(user.getId()).isNotNull();
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaaa-aaaaaaaaa");
    }

    @Test
    void userUpdateDto를_이용하여_유저를_수정할_수_있다() {
        //given
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .address("GangNam")
                .nickname("yongjin")
                .build();

        //when
        userService.update(1, userUpdateDto);

        //then
        User user = userService.getById(1);
        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("GangNam");
        assertThat(user.getNickname()).isEqualTo("yongjin");
    }

    @Test
    void user를_로그인_시키면_마지막_로그인_시간이_변경된다() {
        // given
        // when
        userService.login(1);

        // then
        User user = userService.getById(1);
        assertThat(user.getLastLoginAt()).isEqualTo(1678530673958L);
    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        // given
        // when
        userService.verifyEmail(2, "aaaaaaaaa-aaaaaaaaa");

        // then
        User user = userService.getById(2);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            userService.verifyEmail(2, "aaaaaaaaa-aaaaaaaaabb");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);
    }


}
