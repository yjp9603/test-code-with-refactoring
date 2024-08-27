package com.example.demo.user.domain;

import org.junit.jupiter.api.Test;

public class UserTest {

    @Test
    public void User는_UserCreateDto_객체로_생성할_수_있다() {}

    @Test
    public void User는_UserUpdateDto_객체로_수정할_수_있다() {}

    @Test
    public void User는_로그인을_할_수_있고_로그인시_마지막_로그인_시간이_변경된다() {}


    @Test
    public void User는_유효한_인증코드로_계정을_활성화_할_수_있다() {}

    @Test
    public void User는_잘못된_인증코드로_계정_활성화를_시도하면_에러를_던진다() {}


}
