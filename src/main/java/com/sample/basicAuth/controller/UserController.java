package com.sample.basicAuth.controller;

import com.sample.basicAuth.dao.User;
import com.sample.basicAuth.exception.UserCustomException;
import com.sample.basicAuth.service.UserService;
import com.sample.basicAuth.utils.Routes;
import com.sample.basicAuth.utils.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(Routes.USER)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(Routes.SIGNUP)
    public ResponseEntity<?> signUp(@RequestBody User user) throws Exception {
        return ResponseEntity.ok(userService.signUp(user));
    }

    @PostMapping(Routes.LOGIN)
    public ResponseEntity<?> login(@RequestBody User user) throws Exception {
        if (user.getEmailAddress() == null || user.getPassword() == null) {
            throw new UserCustomException(UserMessage.INVALID_CREDENTIALS);
        }

        return ResponseEntity.ok(userService.login(user));
    }


    @GetMapping(Routes.PROFILE)
    public ResponseEntity<?> profile(@PathVariable String username) throws UserCustomException {
        User userInfo = userService.getUserInfo(username);
        if (userInfo == null) {
            throw new UserCustomException(UserMessage.INVALID_CREDENTIALS);
        }

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping(Routes.ALL)
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(userService.getInfo());
    }
}
