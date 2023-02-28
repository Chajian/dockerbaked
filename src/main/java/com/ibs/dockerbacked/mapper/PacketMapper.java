package com.ibs.dockerbacked.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ibs.dockerbacked.entity.Packet;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PacketMapper extends BaseMapper<Packet> {
}
