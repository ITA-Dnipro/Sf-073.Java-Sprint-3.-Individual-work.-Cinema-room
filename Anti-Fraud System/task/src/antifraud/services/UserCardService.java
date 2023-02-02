package antifraud.services;

import antifraud.models.database.UserCard;

import java.util.Optional;

public interface UserCardService {

    UserCard saveCard(UserCard card);
    Optional<UserCard> findLastUserCardByNumber(String number);
}
