package org.example.interceptor;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.example.conntext.BaseContext;
import org.example.constant.JwtClaimsConstant;
import org.example.properties.JWTProperties;
import org.example.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtTokenEmployeeInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTProperties jwtProperties;

    //校验token
    public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handle){
        //不为处理器方法放行
        if (!(handle instanceof HandlerMethod)){
            return true;
        }
        String token = request.getHeader(jwtProperties.getEmployeeTokenName());
        try {
            log.info("jwt校验：{}",token);
            Claims claims = JwtUtils.parseJW(jwtProperties.getEmployeeSecretKey(), token);
            Long empId = Long.valueOf(claims.get(JwtClaimsConstant.EMP_ID).toString());
            BaseContext.setCurrentId(empId);
            log.info("当前员工id为：{}",empId);
            return true;
        }
        catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
