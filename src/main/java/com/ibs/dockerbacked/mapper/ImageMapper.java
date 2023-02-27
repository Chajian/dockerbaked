package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.data.ImageEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper extends BaseMapper<ImageEntity> {
}
