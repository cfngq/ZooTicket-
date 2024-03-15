package org.example.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.example.conntext.BaseContext;
import org.example.constant.MessageConstant;
import org.example.constant.TicketStatusConstant;
import org.example.constant.TicketTypeConstant;
import org.example.constant.TicketTypePriceConstant;
import org.example.dto.CreateTicketDTO;
import org.example.dto.TicketDTO;
import org.example.dto.TicketPageQueryDTO;
import org.example.entity.Ticket;
import org.example.exception.AccountNoAbleStatusException;
import org.example.exception.TicketAlreadyExistsException;
import org.example.mapper.TicketMapper;
import org.example.result.PageResult;
import org.example.service.TicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketMapper ticketMapper;

    public void addByUser(CreateTicketDTO createTicketDTO) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(createTicketDTO,ticket);
        Ticket ticket1 = ticketMapper.getByTicketName(ticket.getName());
        if (ticket1 != null) {
            Duration duration = Duration.between(ticket1.getCreateTime(), LocalDateTime.now());
            //票务是否存在
            if (duration.toHours() < 24) {
                throw new TicketAlreadyExistsException(MessageConstant.TICKET_ALREADY_EXISTS);
            }
        }
        Long userId = BaseContext.getCurrentId();
        ticket.setUserId(userId);
        //票务类型
        if (ticket.getType().equals(TicketTypeConstant.ADULT_TICKET)){
            ticket.setPrice(TicketTypePriceConstant.ADULT_TICKET_PRICE);
        }if (ticket.getType().equals(TicketTypeConstant.CHILD_TICKET)){
            ticket.setPrice(TicketTypePriceConstant.CHILD_TICKET_PRICE);
        }
        ticket.setStatus(TicketStatusConstant.AN_ABLE_STATUS);
        ticketMapper.insert(ticket);
    }


    public void addByEmployee(CreateTicketDTO createTicketDTO) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(createTicketDTO,ticket);
        Ticket ticket1 = ticketMapper.getByTicketName(ticket.getName());
        //票务是否存在
        if (ticket1 != null){
            throw new TicketAlreadyExistsException(MessageConstant.TICKET_ALREADY_EXISTS);
        }
        Long employeeId = BaseContext.getCurrentId();
        ticket.setEmployeeId(employeeId);
        //票务类型
        if (ticket.getType().equals(TicketTypeConstant.ADULT_TICKET)){
            ticket.setPrice(TicketTypePriceConstant.ADULT_TICKET_PRICE);
        }if (ticket.getType().equals(TicketTypeConstant.CHILD_TICKET)){
            ticket.setPrice(TicketTypePriceConstant.CHILD_TICKET_PRICE);
        }
        ticket.setStatus(TicketStatusConstant.AN_ABLE_STATUS);
        ticketMapper.insert(ticket);
    }


    public Ticket getById(Long id) {
        Ticket ticket = ticketMapper.gteById(id);
        return ticket;
    }

    public PageResult list(TicketPageQueryDTO ticketPageQueryDTO) {
        PageHelper.startPage(ticketPageQueryDTO.getPageNum(),ticketPageQueryDTO.getPageSize());
        Page<Ticket> page = ticketMapper.pageQuery(ticketPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    public void updateStatusById(Long id) {
        Ticket ticket = Ticket.builder()
                .id(id)
                .status(TicketStatusConstant.NO_ABLE_STATUS)
                .build();
        ticketMapper.update(ticket);
    }

    public void update(TicketDTO ticketDTO) {
        Ticket ticket = new Ticket();
        BeanUtils.copyProperties(ticketDTO,ticket);
        ticketMapper.update(ticket);
    }


    public void delete(Long id) {
        Long currentId = BaseContext.getCurrentId();
        Ticket ticket = ticketMapper.gteById(id);
        //是否为购买票务的用户操作
        if (!(ticket.getUserId().equals(currentId)) && !(ticket.getEmployeeId().equals(currentId))){
            throw  new AccountNoAbleStatusException(MessageConstant.USER_COMPARISON_FALSE);
        }
        //该票务是否使用
        if (ticket.getStatus().equals(TicketStatusConstant.NO_ABLE_STATUS)){
            throw new TicketAlreadyExistsException(MessageConstant.TICKET_ALREADY_USE);
        }
        ticketMapper.delete(ticket.getId());
    }


    public List<Ticket> getByPhone(String phone) {
        List<Ticket> ticketList = ticketMapper.getByPhone(phone);
        return ticketList;
    }
}
