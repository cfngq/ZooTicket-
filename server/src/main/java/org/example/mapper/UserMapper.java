package org.example.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.annotation.AuToFill;
import org.example.entity.Employee;
import org.example.entity.User;
import org.example.enumeration.OperationType;

@Mapper
public interface UserMapper {

    @Select("select * from user where phone = #{phone}")
    User getByPhone(String phone);

    @AuToFill(OperationType.INSERT)
    void insert(User user);
}
