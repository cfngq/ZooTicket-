package org.example.service;

import org.example.dto.EmployeeDTO;
import org.example.dto.EmployeeLoginDTO;
import org.example.dto.EmployeePageQueryDTO;
import org.example.dto.EmployeeUpdateDTO;
import org.example.entity.Employee;
import org.example.result.PageResult;


public interface EmployeeService {
    Employee login(EmployeeLoginDTO employeeLoginDTO);
    void add(EmployeeDTO employeeDTO);

    void updateStatusById(Long id, Integer status);

    void update(EmployeeUpdateDTO employeeUpdateDTO);

    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);
}
