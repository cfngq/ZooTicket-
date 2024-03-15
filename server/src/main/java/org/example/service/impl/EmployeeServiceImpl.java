package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.constant.*;
import org.example.dto.*;
import org.example.entity.Employee;
import org.example.entity.Ticket;
import org.example.exception.*;
import org.example.mapper.EmployeeMapper;
import org.example.result.PageResult;
import org.example.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUserName();
        String password = employeeLoginDTO.getPassWord();
        Employee employee = employeeMapper.getByUsername(username);
        //账号是否存在
        if (employee == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        //员工是否处于工作状态
        if(employee.getStatus().equals(EmployeeStatusConstant.NO_ABLE_STATUS)){
            throw new AccountNoAbleStatusException(MessageConstant.ACCOUNT_NO_ABLE_STATUS);
        }
        //密码比对(md5)
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassWord())){
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }
        return employee;
    }
    public void add(EmployeeDTO employeeDTO) {
        Employee byUsername = employeeMapper.getByUsername(employeeDTO.getUserName());
        //账号是否存在
        if (byUsername != null){
            throw  new AccountAlreadyExistsException(MessageConstant.ACCOUNT_ALREADY_EXISTS);
        }
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO,employee);
        employee.setStatus(EmployeeStatusConstant.AN_ABLE_STATUS);
        employee.setPassWord("e10adc3949ba59abbe56e057f20f883e");
        employeeMapper.insert(employee);
    }

    public void updateStatusById(Long id, Integer status) {
            Employee employee = Employee.builder()
                    .id(id)
                    .status(status)
                    .build();
            employeeMapper.update(employee);
    }


    public void update(EmployeeUpdateDTO employeeUpdateDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeUpdateDTO,employee);
        employeeMapper.update(employee);
    }


    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {
        PageHelper.startPage(employeePageQueryDTO.getPageNum(),employeePageQueryDTO.getPageSize());
        Page<Employee> employeePage = employeeMapper.pageQuery(employeePageQueryDTO);
        return new PageResult(employeePage.getTotal(),employeePage.getResult());
    }

}

