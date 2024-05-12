package org.oop.sns.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.oop.sns.exception.ErrorCode;
import org.oop.sns.exception.SnsApplicationException;
import org.oop.sns.model.User;
import org.oop.sns.model.entity.UserEntity;
import org.oop.sns.repository.UserEntityRepository;
import org.oop.sns.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Value("${jwt.token.expired-time-ms}")
    private Long tokenExpiredTimeMs;

    @Transactional
    public User join(String userName, String password) {
        // 회원가입하려는 username이 존재하는 user가 있는지
        userRepository.findByUserName(userName).ifPresent(it -> {
            throw new SnsApplicationException(ErrorCode.DUPLICATE_USERNAME, String.format("%s is duplicated", userName));
        });

        // 회원가입 진행 = user를 등록
        UserEntity userEntity = userRepository.save(UserEntity.of(userName, encoder.encode(password)));
        return User.fromEntity(userEntity);
    }

    public String login(String userName, String password) {
        // 회원가입 여부 체크
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(ErrorCode.USER_NOT_FOUND, String.format("%s not found", userName)));

        // 비밀번호 체크
//        if (!userEntity.getPassword().equals(encoder.encode(password))) {
//            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
//        }
        if (!encoder.matches(password, userEntity.getPassword())) {
            throw new SnsApplicationException(ErrorCode.INVALID_PASSWORD);
        }

        // 토큰 생성

        return JwtTokenUtils.generateToken(userName, secretKey, tokenExpiredTimeMs);
    }
}
