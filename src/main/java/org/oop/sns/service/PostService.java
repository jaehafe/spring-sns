package org.oop.sns.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.oop.sns.exception.ErrorCode;
import org.oop.sns.exception.SnsApplicationException;
import org.oop.sns.model.entity.PostEntity;
import org.oop.sns.model.entity.UserEntity;
import org.oop.sns.repository.PostEntityRepository;
import org.oop.sns.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {

        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() ->
                        new SnsApplicationException(
                                ErrorCode.USER_NOT_FOUND,
                                String.format("%s not found", userName
                                )
                        )
                );

        // post save
        PostEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));


        // return

    }
}
