package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.data.RoseEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoseMapper extends BaseMapper<RoseEntity> {
}
