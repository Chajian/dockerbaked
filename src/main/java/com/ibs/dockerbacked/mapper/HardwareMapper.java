package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.entity.Hardware;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HardwareMapper extends BaseMapper<Hardware> {
}
