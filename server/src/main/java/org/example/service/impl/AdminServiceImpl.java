package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.constant.EmployeeStatusConstant;
import org.example.constant.MessageConstant;
import org.example.dto.*;
import org.example.entity.Admin;
import org.example.entity.Employee;
import org.example.exception.AccountAlreadyExistsException;
import org.example.exception.AccountNotFoundException;
import org.example.exception.PasswordErrorException;
import org.example.mapper.AdminMapper;
import org.example.mapper.EmployeeMapper;
import org.example.result.PageResult;
import org.example.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    public Admin login(AdminLoginDTO adminLoginDTO) {
        String username = adminLoginDTO.getUserName();
        String password = adminLoginDTO.getPassWord();
        Admin admin = adminMapper.getByUsername(username);
        //账号是否存在
        if (admin == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //密码比对(md5)
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(admin.getPassWord())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return admin;
    }

}
