package antifraud.services;

import antifraud.domain.models.dto.UserDto;
import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.models.request.UpdateUserIsActiveStatusRequest;
import antifraud.domain.models.request.UpdateUserRoleRequest;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserSignUpResponse;

import java.util.List;


public interface UserService {

    UserSignUpResponse getUserSignUpResponse(UserSignUpRequest userSignUpRequest);
    UserDto createUser(UserDto userDto);
    List<Object> getAllRegisteredUsers();

    void deleteUser(UserEntity userEntity);
    UserSignUpResponse getUserSignUpResponse(UserEntity userEntity, UpdateUserRoleRequest updateUserRoleRequest);

    UserDto updateUserRole(UserDto userDto);

    void setUserIsActiveStatus(UserEntity userRecord, UpdateUserIsActiveStatusRequest updateUserIsActiveStatusRequest);
}
