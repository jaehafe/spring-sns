package org.oop.sns.service;

import lombok.RequiredArgsConstructor;
import org.oop.sns.exception.ErrorCode;
import org.oop.sns.exception.SnsApplicationException;
import org.oop.sns.model.User;
import org.oop.sns.model.entity.UserEntity;
import org.oop.sns.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userRepository;

    // TODO : implement
    public User join(String username, String password) {
        // 회원가입하려는 username이 존재하는 user가 있는지
        userRepository.findByUserName(username).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USERNAME);
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userRepository.save(UserEntity.of(username, password));

        return User.fromEntity(userEntity);
    }

    // TODO : implement
    public String login(String username, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userRepository.findByUserName(username)
                .orElseThrow(SnsApplicationException::new);

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException();
        }

        // 토큰 생성

        return "";
    }
}
