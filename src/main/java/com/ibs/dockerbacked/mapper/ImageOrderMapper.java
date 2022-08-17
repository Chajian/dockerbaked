package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.data.ImageOrderEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageOrderMapper extends BaseMapper<ImageOrderEntity> {
}
