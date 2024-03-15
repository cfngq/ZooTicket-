package org.example.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "修改票务信息传递的数据模型")
public class TicketDTO {

    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("类型")
    private String type;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("价格")
    private Integer price;
    @ApiModelProperty("状态")
    private Integer status;

}
