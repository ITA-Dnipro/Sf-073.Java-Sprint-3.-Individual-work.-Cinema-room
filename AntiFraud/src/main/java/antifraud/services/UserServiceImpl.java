package antifraud.services;

import antifraud.domain.models.dto.UserDto;
import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserSignUpResponse;
import antifraud.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserSignUpResponse getUserSignUpResponse(UserSignUpRequest userSignUpRequest) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userSignUpRequest, userDto);
        UserDto newUserDto = createUser(userDto);
        UserSignUpResponse newUserSignUpResponse = new UserSignUpResponse();
        BeanUtils.copyProperties(newUserDto, newUserSignUpResponse);
        return newUserSignUpResponse;
    }
    @Override
    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        userEntity.setName(userDto.getName());
        userEntity.setUserName(userDto.getUserName());
        userEntity.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setActive(true);
        userEntity.setRoles("USER");
        UserEntity userEntityRecord = userRepository.save(userEntity);
        UserDto userDtoResponse = new UserDto();
        BeanUtils.copyProperties(userEntityRecord, userDtoResponse);
        return userDtoResponse;
    }
    @Override
    public List<Object> getAllRegisteredUsers() {
        List<UserEntity> userEntityList = userRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        return userEntityList
                .stream().map(entity -> new UserSignUpResponse(entity.getId(), entity.getName(), entity.getUserName()))
                .collect(Collectors.toList());
    }
    @Override
    public void deleteUser(UserEntity userEntity) {
        userRepository.delete(userEntity);
    }
}
