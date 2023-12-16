package com.sample.basicAuth.controller;

import com.sample.basicAuth.dao.User;
import com.sample.basicAuth.service.UserService;
import com.sample.basicAuth.utils.routes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(routes.USER)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(routes.SIGNUP)
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.signUp(user));
    }

    @PostMapping(routes.LOGIN)
    public ResponseEntity<?> login(@RequestBody User user) {
        if (user.getEmailAddress() == null || user.getPassword() == null) {
            return ResponseEntity.badRequest().body("Enter valid credentials");
        }

        return ResponseEntity.ok(userService.login(user));
    }


    @GetMapping(routes.PROFILE)
    public ResponseEntity<?> profile(@PathVariable String userName) {
        User userInfo = userService.getUserInfo(userName);
        if (userInfo == null) return ResponseEntity.badRequest().body("profile does not exists");

        return ResponseEntity.ok(userInfo);
    }

    @GetMapping(routes.ALL)
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(userService.getInfo());
    }
}
