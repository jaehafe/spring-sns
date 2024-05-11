package org.oop.sns.controller;

import lombok.RequiredArgsConstructor;
import org.oop.sns.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성
public class UserController {

    private final UserService userService;

    // TODO : implement
    @PostMapping
    public void join() {
        userService.join();
    }
}
