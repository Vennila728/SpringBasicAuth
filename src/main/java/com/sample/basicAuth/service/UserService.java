package com.sample.basicAuth.service;

import com.sample.basicAuth.dao.User;
import com.sample.basicAuth.repository.AuthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService implements Authentication<User> {

    private final AuthRepository authRepository;

    public UserService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public String signUp(User user) {
        if (user.getEmailAddress() == null || user.getPassword() == null || user.getName() == null)
            return "Please enter valid data";
        if (checkExistingUser(user.getEmailAddress())) {
            return "User already exists";
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(encrypt(user.getPassword()));
        authRepository.addInfo(user);

        return "SignedUp Successfully";
    }

    private boolean checkExistingUser(String userMail) {
        return authRepository.getAllInfo().stream().anyMatch(existingUser -> existingUser.getEmailAddress().equalsIgnoreCase(userMail));
    }

    @Override
    public String login(User user) {
        if (loginValidation(user)) {
            log.info("loginValidation {}", loginValidation(user));
            return "logged in Successfully";
        }

        return "logged in failed Please check the credentials";
    }

    private boolean loginValidation(User user) {
        return authRepository.getAllInfo().stream()
                .anyMatch(userData -> userData.getEmailAddress().equals(user.getEmailAddress())
                        && userData.getPassword().equals(encrypt(user.getPassword())));
    }

    @Override
    public User getUserInfo(String userName) {
        return authRepository.getAllInfo().stream()
                .filter(userData -> userData.getName().equalsIgnoreCase(userName))
                .findFirst()
                .orElse(null);

    }

    @Override
    public Collection<User> getInfo() {
        return authRepository.getAllInfo();
    }

    @Override
    public String encrypt(String password) {
        return Authentication.super.encrypt(password);
    }

}

