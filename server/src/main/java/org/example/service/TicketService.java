package org.example.service;

import org.example.dto.CreateTicketDTO;
import org.example.dto.TicketDTO;
import org.example.dto.TicketPageQueryDTO;
import org.example.entity.Ticket;
import org.example.result.PageResult;

import java.util.List;

public interface TicketService {
     void addByUser(CreateTicketDTO createTicketDTO);
    void addByEmployee(CreateTicketDTO createTicketDTO);

    Ticket getById(Long id);

    PageResult list(TicketPageQueryDTO ticketPageQueryDTO);

    void updateStatusById(Long id);

    void update(TicketDTO ticketDTO);

    void delete(Long id);

    List<Ticket> getByPhone(String phone);
}
