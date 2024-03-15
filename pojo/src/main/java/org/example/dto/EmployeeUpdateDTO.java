package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "注册员工时传递的数据模型")
public class EmployeeUpdateDTO {

    @ApiModelProperty("主键值")
    private Long id;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("员工状态")
    private Integer status;

}
