package com.ibs.dockerbacked.entity.dto;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author sm
 * @version 1.0
 * @descript 分页查询参数
 * @date 2023/3/4 20:15
 */
@Data
@ToString
public class PageParam {
    private Integer page;
    private Integer pageSize;
}
