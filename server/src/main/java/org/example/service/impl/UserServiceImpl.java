package org.example.service.impl;

import org.example.constant.MessageConstant;
import org.example.dto.UserDTO;
import org.example.dto.UserLoginDTO;
import org.example.entity.User;
import org.example.exception.AccountAlreadyExistsException;
import org.example.exception.AccountNotFoundException;
import org.example.exception.PasswordErrorException;
import org.example.mapper.EmployeeMapper;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    public User login(UserLoginDTO userLoginDTO) {
        User user = userMapper.getByPhone(userLoginDTO.getPhone());
        //账户是否存在
        if (user == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        String passWord = userLoginDTO.getPassWord();
        passWord = DigestUtils.md5DigestAsHex(passWord.getBytes());
        if (!(user.getPassword().equals(passWord))){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return user;
    }


    public void add(UserDTO userDTO) {
        User byPhone = userMapper.getByPhone(userDTO.getPhone());
        if (byPhone != null){
            throw new AccountAlreadyExistsException(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(password);
        userMapper.insert(user);
    }
}

