package org.example.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Admin;


@Mapper
public interface AdminMapper {

    @Select("select  * from admin where userName = #{userName}")
    Admin getByUsername(String userName);

}
