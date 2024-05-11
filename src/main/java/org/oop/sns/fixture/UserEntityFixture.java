package org.oop.sns.fixture;

import org.oop.sns.model.entity.UserEntity;

public class UserEntityFixture {

    public static UserEntity get(String username, String password) {
        UserEntity result = new UserEntity();
        result.setId(1);
        result.setUserName(username);
        result.setPassword(password);

        return result;
    }
}
