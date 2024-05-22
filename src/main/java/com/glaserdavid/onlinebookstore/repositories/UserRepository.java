package com.glaserdavid.onlinebookstore.repositories;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.exceptions.AuthException;

public interface UserRepository {

    Integer create(String username, String email, String password) throws AuthException;

    User findByEmailAndPassword(String email, String password) throws AuthException;

    Integer getCountyByEmail(String email);

    User findById(Integer userId);
}