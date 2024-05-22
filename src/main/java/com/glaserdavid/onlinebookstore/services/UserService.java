package com.glaserdavid.onlinebookstore.services;

import com.glaserdavid.onlinebookstore.domain.User;
import com.glaserdavid.onlinebookstore.exceptions.AuthException;

public interface UserService {

    User validateUser(String username, String email, String password) throws AuthException;

    User registerUser(String username, String email, String password) throws AuthException;


}
