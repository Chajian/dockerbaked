package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.data.ContainerOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContainerOrderMapper extends BaseMapper<ContainerOrderEntity> {
}
