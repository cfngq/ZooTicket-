package org.example.result;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    //返回结果
    private Integer code;
    private String msg;
    private T data;

    //无参成功返回
    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.code=1;
        return result;
    }
    //有参成功返回
    public static <T> Result<T> success(T object){
        Result<T> result = new Result<>();
        result.code=1;
        result.data=object;
        return result;
    }
    //错误返回
    public static <T> Result<T> error(String msg){
        Result<T> result = new Result<>();
        result.code=0;
        result.msg=msg;
        return result;
    }
}
