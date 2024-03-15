package org.example.exception;

public class AccountNoAbleStatusException extends BaseException{
    public AccountNoAbleStatusException(){}
    public AccountNoAbleStatusException(String msg){
        super(msg);
    }
}
