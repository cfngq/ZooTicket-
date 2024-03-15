package org.example.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.JwtClaimsConstant;
import org.example.dto.CreateTicketDTO;
import org.example.dto.UserDTO;
import org.example.dto.UserLoginDTO;
import org.example.entity.Ticket;
import org.example.entity.User;
import org.example.properties.JWTProperties;
import org.example.result.Result;
import org.example.service.TicketService;
import org.example.service.UserService;
import org.example.utils.JwtUtils;
import org.example.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
@Api(tags = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTProperties jwtProperties;

    @Autowired
    private TicketService ticketService;
    @PostMapping("/login")
    @ApiOperation("用户登录")
    public Result<UserLoginVO> login(UserLoginDTO userLoginDTO){
        User user =  userService.login(userLoginDTO);
        Map<String,Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.User_ID,user.getId());
        String token = JwtUtils.creatJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO userLoginVO = UserLoginVO.builder()
                .phone(user.getPhone())
                .name(user.getName())
                .token(token)
                .build();
        log.info("用户登录：{}",userLoginDTO);
        return Result.success(userLoginVO);
    }

    @PostMapping("/add")
    @ApiOperation("用户注册")
    public Result<String> add(@RequestBody UserDTO userDTO){
        log.info("新用户：{}",userDTO);
        userService.add(userDTO);
        return Result.success();
    }

    @PostMapping("/buy")
    @ApiOperation("用户购票")
    public Result<String> buy(@RequestBody CreateTicketDTO createTicketDTO){
        log.info("用户购票：{}",createTicketDTO);
        ticketService.addByUser(createTicketDTO);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("用户退票")
    public Result<String> delete(Long id){
        log.info("用户退票：{}",id);
        ticketService.delete(id);
        return Result.success();
    }

    @GetMapping("/phone/{phone}")
    @ApiOperation("通过手机号查询门票")
    public Result<List<Ticket>> getByPhone(@PathVariable String phone){
        log.info("通过手机号查询门票：{}",phone);
        List<Ticket> ticketList = ticketService.getByPhone(phone);
        return Result.success(ticketList);
    }
}
