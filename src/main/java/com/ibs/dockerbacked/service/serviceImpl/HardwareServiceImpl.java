package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.service.HardwareService;
import org.springframework.stereotype.Service;

@Service
public class HardwareServiceImpl extends ServiceImpl<HardwareMapper, Hardware> implements HardwareService {
}
