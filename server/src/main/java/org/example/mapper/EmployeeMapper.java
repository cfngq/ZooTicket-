package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.annotation.AuToFill;
import org.example.dto.EmployeePageQueryDTO;
import org.example.entity.Employee;
import org.example.enumeration.OperationType;

@Mapper
public interface EmployeeMapper {

    @Select("select  * from employee where userName = #{userName}")
    Employee getByUsername(String username);
    @AuToFill(OperationType.INSERT)
    void insert(Employee employee);

    @AuToFill(OperationType.UPDATE)
    void update(Employee employee);

    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
}
