package org.oop.sns.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.oop.sns.model.entity.UserEntity;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class User {

    private Integer id;
    private String username;
    private String password;
    private UserRole userRole;
    private Timestamp registeredAt;
    private Timestamp updatedAt;
    private Timestamp deletedAt;

    // entity를 dto로 변환해주는 method
    public static User fromEntity(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getUserName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getRegisteredAt(),
                entity.getUpdatedAt(),
                entity.getDeletedAt()
        );
    }

}
