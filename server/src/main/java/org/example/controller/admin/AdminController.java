package org.example.controller.admin;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.JwtClaimsConstant;
import org.example.dto.*;
import org.example.entity.Admin;
import org.example.entity.Employee;
import org.example.properties.JWTProperties;
import org.example.result.PageResult;
import org.example.result.Result;
import org.example.service.AdminService;
import org.example.service.EmployeeService;
import org.example.utils.JwtUtils;
import org.example.vo.AdminLoginVO;
import org.example.vo.EmployeeLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/admin")
@Slf4j
@Api(tags = "管理员相关接口")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private JWTProperties jwtProperties;

    @Autowired
    private EmployeeService employeeService;
    /**
     *管理员登录
     * @param adminLoginDTO
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("管理员登录接口")
    public Result<AdminLoginVO> login(@RequestBody AdminLoginDTO adminLoginDTO){
        Admin admin = adminService.login(adminLoginDTO);
        Map<String, Object> claims =new HashMap<>();
        claims.put(JwtClaimsConstant.ADM_ID,admin.getId());
        String token = JwtUtils.creatJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);
        AdminLoginVO adminLoginVO = AdminLoginVO.builder()
                .userName(admin.getUserName())
                .name(admin.getName())
                .token(token).
                build();
        log.info("管理员登录：{}",adminLoginVO);
        return Result.success(adminLoginVO);
    }

    @PostMapping("/add")
    @ApiOperation("注册员工接口")
    public Result<String> add(@RequestBody EmployeeDTO employeeDTO){
        log.info("注册员工：{}",employeeDTO);
        employeeService.add(employeeDTO);
        return Result.success();
    }

    @PostMapping("/status")
    @ApiOperation("修改员工状态")
    public Result<String> status(@RequestParam Long id, Integer status){
        log.info("修改员工：{},修改状态为：{}",id,status);
        employeeService.updateStatusById(id,status);
        return Result.success();
    }

    @PostMapping("/update")
    @ApiOperation("修改员工信息")
    public Result<String> update(@RequestBody EmployeeUpdateDTO employeeUpdateDTO){
        log.info("修改员工信息：{}",employeeUpdateDTO);
        employeeService.update(employeeUpdateDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        log.info("分页查询员工：{}",employeePageQueryDTO);
        PageResult page = employeeService.page(employeePageQueryDTO);
        return Result.success(page);
    }
}
