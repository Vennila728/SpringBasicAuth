package com.sample.basicAuth.repository;

import com.sample.basicAuth.dao.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AuthRepository {

    private final List<User> userList;


    public AuthRepository() {
        this.userList = new ArrayList<>();
    }

    public String addInfo(User user) {
        userList.add(user);
        return user.getId();
    }


    public List<User> getAllInfo() {
        return userList;
    }

}
