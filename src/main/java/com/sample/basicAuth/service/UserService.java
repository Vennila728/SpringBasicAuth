package com.sample.basicAuth.service;

import com.sample.basicAuth.dao.User;
import com.sample.basicAuth.repository.AuthRepository;
import com.sample.basicAuth.utils.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
@Slf4j
public class UserService implements Authentication<User> {

    private final AuthRepository authRepository;

    public UserService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    @Override
    public String signUp(User user) throws Exception {
        if (user.getEmailAddress() == null || user.getPassword() == null || user.getName() == null)
            throw new Exception(UserMessage.INVALID_CREDENTIALS);
        if (checkExistingUser(user.getEmailAddress())) {
            return UserMessage.USER_EXISTS;
        }
        user.setId(UUID.randomUUID().toString());
        user.setPassword(encrypt(user.getPassword()));
        authRepository.addInfo(user);

        return UserMessage.SIGNUP_SUCCESS;
    }

    private boolean checkExistingUser(String userMail) {
        return authRepository.getAllInfo().stream()
                .anyMatch(existingUser -> existingUser.getEmailAddress().equalsIgnoreCase(userMail));
    }

    @Override
    public String login(User user) throws Exception {
        if (loginValidation(user)) {
            log.info("LoginValidation {}", loginValidation(user));
            return UserMessage.LOGIN_SUCCESS;
        }

        throw new Exception(UserMessage.LOGIN_FAILED);
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

