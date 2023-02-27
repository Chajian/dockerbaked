package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.data.ContainerEntity;
import org.apache.ibatis.annotations.Mapper;

import java.awt.*;

@Mapper
public interface ContainerMapper extends BaseMapper<ContainerEntity> {
}
