package org.example.service;

import org.example.dto.UserDTO;
import org.example.dto.UserLoginDTO;
import org.example.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);

    void add(UserDTO userDTO);
}
