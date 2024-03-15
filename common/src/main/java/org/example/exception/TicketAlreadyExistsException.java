package org.example.exception;

public class TicketAlreadyExistsException extends BaseException{
    public TicketAlreadyExistsException(){}
    public TicketAlreadyExistsException(String msg){
        super(msg);
    }
}
