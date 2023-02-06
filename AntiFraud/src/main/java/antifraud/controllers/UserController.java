package antifraud.controllers;

import antifraud.domain.models.dao.UserEntity;
import antifraud.domain.models.request.UpdateUserIsActiveStatusRequest;
import antifraud.domain.models.request.UpdateUserRoleRequest;
import antifraud.domain.models.request.UserSignUpRequest;
import antifraud.domain.models.response.UserDeleteResponse;
import antifraud.domain.models.response.UserIsActiveStatusResponse;
import antifraud.domain.models.response.UserSignUpResponse;
import antifraud.domain.security.UserRole;
import antifraud.repositories.UserRepository;
import antifraud.services.contracts.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api")
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
    @PreAuthorize(value="hasAnyRole('ADMINISTRATOR', 'SUPPORT')")
    @GetMapping(path="/auth/list")
    public ResponseEntity<List<Object>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllRegisteredUsers(), HttpStatus.OK);
    }
    @PreAuthorize(value="hasRole('ADMINISTRATOR')")
    @DeleteMapping ( value ="/auth/user/{username}")
    public ResponseEntity<UserDeleteResponse> deleteUserByName(@PathVariable("username") String username){
        Optional<UserEntity> userRecord = userRepository.findByUserName(username);
        if(userRecord.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.deleteUser(userRecord.get());
        return new ResponseEntity<>(new UserDeleteResponse(userRecord.get().getUserName(), "Deleted successfully!"), HttpStatus.OK);
    }
    @PreAuthorize(value="hasRole('ADMINISTRATOR')")
    @PutMapping(path="/auth/role")
    public ResponseEntity<UserSignUpResponse> updateUserRole(@Valid @RequestBody UpdateUserRoleRequest updateUserRoleRequest){
        if(!userService.validateIfRoleIsPresent(updateUserRoleRequest.getRole())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        if(updateUserRoleRequest.getRole().equals(UserRole.ADMINISTRATOR.name())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<UserEntity> userRecord = userRepository.findByUserName(updateUserRoleRequest.getUserName());
        if(userRecord.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(userRecord.get().getRole().equals(updateUserRoleRequest.getRole())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<>(userService.getUserSignUpResponse(userRecord.get(), updateUserRoleRequest), HttpStatus.OK);
    }
    @PreAuthorize(value="hasRole('ADMINISTRATOR')")
    @PutMapping(path="/auth/access")
    public ResponseEntity<UserIsActiveStatusResponse> updateUserIsActiveStatus(@Valid @RequestBody UpdateUserIsActiveStatusRequest updateUserIsActiveStatusRequest){
        Optional<UserEntity> userRecord = userRepository.findByUserName(updateUserIsActiveStatusRequest.getUserName());
        if(userRecord.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        if(userRecord.get().getRole().equals(UserRole.ADMINISTRATOR.name())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.setUserIsActiveStatus(userRecord.get(), updateUserIsActiveStatusRequest);
        UserIsActiveStatusResponse response = new UserIsActiveStatusResponse();
        response.setStatus(userRecord.get().getUserName(), updateUserIsActiveStatusRequest.getOperation());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
