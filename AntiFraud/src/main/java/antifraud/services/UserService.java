package antifraud.services;

import antifraud.domain.models.dto.UserDto;
import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserSignUpResponse;

import java.util.List;


public interface UserService {

    UserSignUpResponse getUserSignUpResponse(UserSignUpRequest userSignUpRequest);
    UserDto createUser(UserDto userDto);
    List<Object> getAllRegisteredUsers();

    void deleteUser(UserEntity userEntity);
}
