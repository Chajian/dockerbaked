package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.mapper.PacketMapper;
import com.ibs.dockerbacked.service.PacketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class PacketServiceImpl extends ServiceImpl<PacketMapper, Packet> implements PacketService {
}
