package upb.edu.co.fairticket.adapter.out.persistence.mapper;

import upb.edu.co.fairticket.adapter.out.persistence.entity.UserJpaEntity;
import upb.edu.co.fairticket.domain.model.User;
import upb.edu.co.fairticket.domain.model.valueobjects.Email;

public class UserPersistenceMapper {
    private UserPersistenceMapper() {}

    public static User toDomain(UserJpaEntity entity) {
        return new User(
                entity.getId(),
                entity.getName(),
                new Email(entity.getEmail()),
                entity.getPasswordHash(),
                entity.getRole(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    public static UserJpaEntity toJpa(User user) {
        return new UserJpaEntity(
                user.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getPasswordHash(),
                user.getRole(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
