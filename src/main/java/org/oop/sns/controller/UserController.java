package org.oop.sns.controller;

import lombok.RequiredArgsConstructor;
import org.oop.sns.controller.request.UserJoinRequest;
import org.oop.sns.controller.request.UserLoginRequest;
import org.oop.sns.controller.response.Response;
import org.oop.sns.controller.response.UserJoinResponse;
import org.oop.sns.controller.response.UserLoginResponse;
import org.oop.sns.model.User;
import org.oop.sns.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor // 초기화 되지 않은 final 필드나, @NonNull 이 붙은 필드에 대해 생성자를 생성, @Autowired 작성 안해도 됨
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest request) {
        User user = userService.join(request.getUserName(), request.getPassword());
        return Response.success(UserJoinResponse.fromUser(user));
    }

    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest request) {
        String token = userService.login(request.getUserName(), request.getPassword());
        System.out.println("token+" + token);
        return Response.success(new UserLoginResponse(token));
    }
}
