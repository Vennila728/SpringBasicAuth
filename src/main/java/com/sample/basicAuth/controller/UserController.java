package com.sample.basicAuth.controller;

import com.sample.basicAuth.dao.User;
import com.sample.basicAuth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sample.basicAuth.utils.Routes.USER_ROUTE;

@RequestMapping(USER_ROUTE)
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup") // move routes to Routes.java
    public ResponseEntity<?> signUp(@RequestBody User user) {
        return ResponseEntity.ok(userService.signUp(user));
    }

    @PostMapping("/login") // move routes to Routes.java
    public ResponseEntity<?> login(@RequestBody User user) {
        // handle negative scenario first
        if (user.getEmailAddress() != null && user.getPassword() != null) {
            return ResponseEntity.ok(userService.login(user));
        }

        return ResponseEntity.badRequest().body("Enter valid credentials");
    }


    // change to route "profile"
    @GetMapping("/myprofile/{userName}")
    public ResponseEntity<?> myProfile(@PathVariable String userName) {
        User userInfo = userService.getUserInfo(userName);
        if (userInfo == null) return ResponseEntity.badRequest().body("profile does not exists");
        return ResponseEntity.ok(userInfo);
    }

    // rename to meaningful route name
    @GetMapping("/getuserinfo")
    public ResponseEntity<?> getAllUserInfo() {
        return ResponseEntity.ok(userService.getInfo());
    }
}
