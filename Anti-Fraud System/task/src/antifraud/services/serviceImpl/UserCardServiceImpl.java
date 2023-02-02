package antifraud.services.serviceImpl;

import antifraud.models.database.UserCard;
import antifraud.repositories.UserCardRepository;
import antifraud.services.UserCardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCardServiceImpl implements UserCardService {

    private final UserCardRepository userCardRepository;

    @Override
    public UserCard saveCard(UserCard card) {
        return userCardRepository.save(card);
    }

    @Override
    public Optional<UserCard> findLastUserCardByNumber(String number) {
        return userCardRepository.findLastByNumber(number);
    }
}
