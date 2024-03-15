package org.example.controller.employee;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.JwtClaimsConstant;
import org.example.dto.CreateTicketDTO;
import org.example.dto.EmployeeLoginDTO;
import org.example.dto.TicketDTO;
import org.example.dto.TicketPageQueryDTO;
import org.example.entity.Employee;
import org.example.entity.Ticket;
import org.example.properties.JWTProperties;
import org.example.result.PageResult;
import org.example.result.Result;
import org.example.service.EmployeeService;
import org.example.service.TicketService;
import org.example.utils.JwtUtils;
import org.example.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@Slf4j
@Api(tags = "员工相关接口")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private JWTProperties jwtProperties;

    @Autowired
    private TicketService ticketService;

    /**
     *员工登录
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("员工登录接口")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO){
        Employee employee = employeeService.login(employeeLoginDTO);
        Map<String, Object> claims =new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,employee.getId());
        String token = JwtUtils.creatJWT(
                jwtProperties.getEmployeeSecretKey(),
                jwtProperties.getEmployeeTtl(),
                claims);
        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUserName())
                .name(employee.getName())
                .token(token).
                build();
        log.info("员工登录：{}",employeeLoginVO);
        return Result.success(employeeLoginVO);
    }

    @PostMapping("/add")
    @ApiOperation("用户购票")
    public Result<String> add(@RequestBody CreateTicketDTO createTicketDTO){
        log.info("用户购票：{}",createTicketDTO);
        ticketService.addByEmployee(createTicketDTO);
        return Result.success();
    }

    @GetMapping("/id/{id}")
    @ApiOperation("根据票务id查询票务")
    public Result<Ticket> getBtId(@PathVariable Long id){
        log.info("根据票务id查询票务：{}",id);
        Ticket ticket = ticketService.getById(id);
        return Result.success(ticket);
    }

    @GetMapping("/page")
    @ApiOperation("票务分页查询")
    public Result<PageResult> list(TicketPageQueryDTO ticketPageQueryDTO){
        log.info("票务分页查询：{}",ticketPageQueryDTO);
        PageResult page = ticketService.list(ticketPageQueryDTO);
        return Result.success(page);
    }

    @PostMapping("/status/{id}")
    @ApiOperation("修改票务状态")
    public Result<String> status(@PathVariable Long id){
        log.info("修改票务：{}",id);
        ticketService.updateStatusById(id);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改票务信息")
    public Result<String> update(@RequestBody TicketDTO ticketDTO){
        log.info("修改票务信息：{}",ticketDTO);
        ticketService.update(ticketDTO);
        return Result.success();
    }

    @PostMapping("/delete")
    @ApiOperation("退票")
    public Result<String> delete(Long id){
        log.info("退票：{}",id);
        ticketService.delete(id);
        return Result.success();
    }
}
