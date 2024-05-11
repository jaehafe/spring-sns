package org.oop.sns.controller.response;

import lombok.AllArgsConstructor;
import org.oop.sns.model.User;
import org.oop.sns.model.UserRole;

@AllArgsConstructor
public class UserJoinResponse {

    private Integer id;
    private String userName;
    private UserRole role;

    public static UserJoinResponse fromUser(User user) {
        return new UserJoinResponse(
                user.getId(),
                user.getUsername(),
                user.getUserRole()
        );
    }
}
