package com.sample.basicAuth.dao;

import lombok.Data;

@Data
public class User {
    private String id;
    private String name;
    private String emailAddress;
    private String password;
}
