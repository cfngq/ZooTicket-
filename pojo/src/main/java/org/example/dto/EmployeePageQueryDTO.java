package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "员工分页查询时数据模型")
public class EmployeePageQueryDTO implements Serializable {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("页数")
    private Integer pageNum;
    @ApiModelProperty("每页数")
    private Integer pageSize;
}
