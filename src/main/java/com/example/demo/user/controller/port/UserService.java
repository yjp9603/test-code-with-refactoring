package com.example.demo.user.controller.port;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserCreateDto;
import com.example.demo.user.domain.UserUpdateDto;

import java.util.Optional;

public interface UserService {
    User getByEmail(String email);

    User getById(long id);

    User create(UserCreateDto userCreateDto);

    User update(long id, UserUpdateDto userUpdateDto);

    void login(long id);

    void verifyEmail(long id, String certificationCode);
}
