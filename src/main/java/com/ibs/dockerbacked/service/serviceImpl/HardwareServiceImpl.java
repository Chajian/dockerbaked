package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.mapper.HardwareMapper;
import com.ibs.dockerbacked.service.HardwareService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class HardwareServiceImpl extends ServiceImpl<HardwareMapper, Hardware> implements HardwareService {
}
