package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.entity.Hardware;

import java.util.List;

/**
 * @author sn
 */
public interface HardwareService extends IService<Hardware> {
    Hardware getHardwareById(int id);

    List<Hardware> getHardwares(int page ,int size);
}
