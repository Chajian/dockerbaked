package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;

/**
 * @author sn
 */
public interface PacketService extends IService<Packet> {
    /**
     *
     * @param hardware
     * @param isFree 是否弹性收费
     * @return
     */
    //创建套餐
    Result<Boolean> createPacket(Hardware hardware, boolean isFree);
}
