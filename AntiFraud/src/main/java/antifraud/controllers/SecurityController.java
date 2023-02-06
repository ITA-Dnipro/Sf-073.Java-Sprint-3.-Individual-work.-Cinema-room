package antifraud.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class SecurityController {

    @GetMapping(path = "api/auth/authentication")
    @ResponseBody
    public Object currentUser(Authentication authentication){
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        userDetails.isAccountNonLocked();
        String result = userDetails.getUsername() + " \n"
                + userDetails.getAuthorities() + "\n"
                + userDetails.isAccountNonLocked() + "\n"
                + userDetails.isEnabled();
        return result;
    }
}
