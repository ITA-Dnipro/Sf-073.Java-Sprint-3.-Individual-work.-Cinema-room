package antifraud.services;

import antifraud.domain.models.dao.UserEntity;
import antifraud.domain.models.dto.UserDto;
import antifraud.domain.models.request.UpdateUserIsActiveStatusRequest;
import antifraud.domain.models.request.UpdateUserRoleRequest;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserSignUpResponse;
import antifraud.domain.security.UserAccess;
import antifraud.domain.security.UserRole;
import antifraud.repositories.UserRepository;
import antifraud.services.contracts.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserSignUpResponse getUserSignUpResponse(UserSignUpRequest userSignUpRequest) {
        UserDto userDto = mapUserSignUpRequestToUserDto(userSignUpRequest);
        UserDto newUserDto = createUser(userDto);
        return mapUserDtoToUserSignUpResponse(newUserDto);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = mapUserDtoToUserEntity(userDto);
        List<Object> userEntityList = getAllRegisteredUsers();
        if(userEntityList.isEmpty()){
            userEntity.setRole(UserRole.ADMINISTRATOR.name());
            userEntity.setActive(true);
        }else{
            userEntity.setRole(UserRole.MERCHANT.name());
            userEntity.setActive(false);
        }
        UserEntity userEntityRecord = userRepository.save(userEntity);
        return mapUserEntityToUserDto(userEntityRecord);
    }

    @Override
    public List<Object> getAllRegisteredUsers() {
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return userEntityList
                .stream().map(entity -> new UserSignUpResponse(entity.getId(),
                        entity.getName(),
                        entity.getUserName(),
                        entity.getRole()))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }

    @Override
    public UserSignUpResponse getUserSignUpResponse(UserEntity userEntity, UpdateUserRoleRequest updateUserRoleRequest) {
        userEntity.setRole(updateUserRoleRequest.getRole());
        UserEntity userEntityUpdatedRecord = userRepository.save(userEntity);
        UserDto updatedRoleUserDto = mapUserEntityToUserDto(userEntityUpdatedRecord);
        return mapUserDtoToUserSignUpResponse(updatedRoleUserDto);
    }

    @Override
    public UserDto updateUserRole(UserDto userDto) {
        UserEntity userEntity = userRepository.findByUserName(userDto.getUserName()).get();
        userEntity.setRole(userDto.getRole());
        UserEntity userEntityUpdatedRecord = userRepository.save(userEntity);
        return mapUserEntityToUserDto(userEntityUpdatedRecord);
    }

    @Override
    public void setUserIsActiveStatus(UserEntity userRecord, UpdateUserIsActiveStatusRequest updateUserIsActiveStatusRequest) {
        if (updateUserIsActiveStatusRequest.getOperation().equals(UserAccess.LOCK.name())) {
            userRecord.setActive(false);
        } else {
            userRecord.setActive(true);
        }
        userRepository.save(userRecord);
    }

    private UserDto mapUserSignUpRequestToUserDto(UserSignUpRequest userSignUpRequest){
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userSignUpRequest, userDto);
        return userDto;
    }


    private UserEntity mapUserDtoToUserEntity(UserDto userDto){
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setName(userDto.getName());
        userEntity.setUserName(userDto.getUserName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setActive(false);
        userEntity.setRole(userDto.getRole());
        return userEntity;
    }

    private UserDto mapUserEntityToUserDto(UserEntity userEntity){
        UserDto userDtoResponse = new UserDto();
        BeanUtils.copyProperties(userEntity, userDtoResponse);
        return userDtoResponse;
    }

    private UserSignUpResponse mapUserDtoToUserSignUpResponse(UserDto userDto){
        UserSignUpResponse newUserSignUpResponse = new UserSignUpResponse();
        BeanUtils.copyProperties(userDto, newUserSignUpResponse);
        return newUserSignUpResponse;
    }

    @Override
    public boolean validateIfRoleIsPresent(String inputRole){
        for (UserRole role: UserRole.values()) {
            if(role.name().equals(inputRole)){
                return true;
            }
        }
        return false;
    }
}
