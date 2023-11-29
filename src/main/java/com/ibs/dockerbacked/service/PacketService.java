package com.ibs.dockerbacked.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.HardwareDto;

import java.util.List;

/**
 * @author sn
 */
public interface PacketService extends IService<Packet> {
    /**
     * @author sn
     * @param hardware
     * @param isFree 是否弹性收费
     * @return
     */
    //创建套餐
    Result<HardwareDto> createPacket(HardwareDto hardware, boolean isFree);

    boolean addHardwarePacketDP(HardwareDto hardware,boolean isFree,float money);

    /**
     * 获取套餐列表
     * @param page
     * @param size
     * @return
     */
    List<Packet> getPackets(int page,int size);
}
