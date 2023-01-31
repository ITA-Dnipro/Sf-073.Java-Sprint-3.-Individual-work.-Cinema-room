package antifraud.controllers;

import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.models.request.UpdateUserIsActiveStatusRequest;
import antifraud.domain.models.request.UpdateUserRoleRequest;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserDeleteResponse;
import antifraud.domain.models.response.UserIsActiveStatusResponse;
import antifraud.domain.models.response.UserSignUpResponse;
import antifraud.domain.security.UserRole;
import antifraud.repositories.UserRepository;
import antifraud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/auth", method = {RequestMethod.GET, RequestMethod.DELETE, RequestMethod.POST})
public class    UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @PostMapping(path = "/user")
    public ResponseEntity<UserSignUpResponse> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        Optional<UserEntity> userRecord = userRepository.findByUserName(userSignUpRequest.getUserName());
        if(userRecord.isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.getUserSignUpResponse(userSignUpRequest), HttpStatus.CREATED);
    }
    @GetMapping(path="/list")
    public ResponseEntity<List<Object>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllRegisteredUsers(), HttpStatus.OK);
    }
    @DeleteMapping(path="/user/{username}")
    public ResponseEntity<UserDeleteResponse> deleteUserByUserName(@PathVariable String username){
        Optional<UserEntity> userRecord = userRepository.findByUserName(username);
        userRecord.ifPresent(userEntity -> userService.deleteUser(userEntity));
        return userRecord.map(userEntity -> new ResponseEntity<>(new UserDeleteResponse(userEntity.getUserName(), "Deleted successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path="/user/role")
    public ResponseEntity<UserSignUpResponse> updateUserRole(@Valid @RequestBody UpdateUserRoleRequest updateUserRoleRequest){
        Optional<UserEntity> userRecord = userRepository.findByUserName(updateUserRoleRequest.getUserName());
        if(userRecord.isPresent() && userRecord.get().getRole().equals(updateUserRoleRequest.getRole())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.getUserSignUpResponse(userRecord.get(), updateUserRoleRequest), HttpStatus.OK);
    }
    @PutMapping(path="/access")
    public ResponseEntity<String> updateUserIsActiveStatus(@Valid @RequestBody UpdateUserIsActiveStatusRequest updateUserIsActiveStatusRequest){
        Optional<UserEntity> userRecord = userRepository.findByUserName(updateUserIsActiveStatusRequest.getUserName());
        if(userRecord.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(userRecord.get().getRole().equals(UserRole.ADMINISTRATOR.name())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.setUserIsActiveStatus(userRecord.get(), updateUserIsActiveStatusRequest);
        return new ResponseEntity<>(new UserIsActiveStatusResponse()
                .setStatus(userRecord.get().getUserName(), updateUserIsActiveStatusRequest.getOperation()), HttpStatus.OK);
    }
}
