package com.ibs.dockerbacked.entity.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author chen
 * @version 1.0
 * @descript 分页查询参数
 * @date 2023/3/4 20:15
 */
@Data
@ToString
@ApiModel("分页配置")
public class PageParam {
    @ApiModelProperty(value = "当前页码")
    private Integer page;
    @ApiModelProperty(value = "每页数量")
    private Integer pageSize;
}
