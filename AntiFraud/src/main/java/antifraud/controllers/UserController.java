package antifraud.controllers;

import antifraud.domain.models.entity.UserEntity;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserDeleteResponse;
import antifraud.domain.models.response.UserSignUpResponse;
import antifraud.repositories.UserRepository;
import antifraud.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @PostMapping(path = "/auth/user")
    public ResponseEntity<UserSignUpResponse> createUser(@Valid @RequestBody UserSignUpRequest userSignUpRequest){
        Optional<UserEntity> userRecord = userRepository.findByUserName(userSignUpRequest.getUserName());
        if(userRecord.isPresent()){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.getUserSignUpResponse(userSignUpRequest), HttpStatus.CREATED);
    }
    @GetMapping(path="/auth/list")
    public ResponseEntity<List<Object>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllRegisteredUsers(), HttpStatus.OK);
    }
    @DeleteMapping(path="/auth/user/{username}")
    public ResponseEntity<UserDeleteResponse> deleteUserByUserName(@PathVariable("username") String userName){
        Optional<UserEntity> userRecord = userRepository.findByUserName(userName);
        userRecord.ifPresent(userEntity -> userService.deleteUser(userEntity));
        return userRecord.map(userEntity -> new ResponseEntity<>(new UserDeleteResponse(userEntity.getUserName(), "Deleted successfully"), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
