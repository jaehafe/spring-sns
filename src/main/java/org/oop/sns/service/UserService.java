package org.oop.sns.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.oop.sns.exception.ErrorCode;
import org.oop.sns.exception.SnsApplicationException;
import org.oop.sns.model.User;
import org.oop.sns.model.entity.UserEntity;
import org.oop.sns.repository.UserEntityRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public User join(String userName, String password) {
        // 회원가입하려는 username이 존재하는 user가 있는지
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USERNAME, String.format("%s is duplicated", userName));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userRepository.save(UserEntity.of(userName, encoder.encode(password)));
//        throw new RuntimeException();
        return User.fromEntity(userEntity);
    }

    // TODO : implement
    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.DUPLICATE_USERNAME, "123"));

        // 비밀번호 체크
        if (!userEntity.getPassword().equals(password)) {
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USERNAME, String.format("%s is duplicated", userName));
        }


        // 토큰 생성

        return "";
    }
}
