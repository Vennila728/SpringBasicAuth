package com.sample.basicAuth.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;

public interface Authentication<T> {

    String signUp(T t);

    String login(T t);

    T getUserInfo(String t);

    Collection<T> getInfo();

    default String encrypt(String password) {
        byte[] encodePassword = Base64.getEncoder().encode(password.getBytes(StandardCharsets.UTF_8));
        return new String(encodePassword);
    }
}
