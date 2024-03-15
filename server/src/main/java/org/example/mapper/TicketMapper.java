package org.example.mapper;

import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.annotation.AuToFill;
import org.example.dto.TicketPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.Ticket;
import org.example.enumeration.OperationType;

import java.util.List;

@Mapper
public interface TicketMapper {

    @Select("SELECT * FROM ticket WHERE name = #{name}")
    Ticket getByTicketName(String name);


    @AuToFill(OperationType.INSERT)
    void insert(Ticket ticket);

    @Select("select * from ticket where id = #{id}")
    Ticket gteById(Long id);

    Page<Ticket> pageQuery(TicketPageQueryDTO ticketPageQueryDTO);

    @AuToFill(OperationType.UPDATE)
    void update(Ticket ticket);

    @Delete("delete from ticket where id = #{id}")
    void delete(Long id);

    @Select("select * from ticket where phone = #{phone}")
    List<Ticket> getByPhone(String phone);
}
