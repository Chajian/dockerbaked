package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.entity.dto.AtomicFloat;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.PacketMapper;
import com.ibs.dockerbacked.service.HardwareService;
import com.ibs.dockerbacked.service.PacketService;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


/**
 * @author sn
 */
@Slf4j
@Service
public class PacketServiceImpl extends ServiceImpl<PacketMapper, Packet> implements PacketService {
    @Autowired
    @Lazy
    private HardwareService hardwareService;

    @Lazy
    @Autowired
    private PacketService currentPorxy;


    @Override
    public Result<HardwareDto> createPacket(HardwareDto hardware, boolean isFree) {

        //弹性收费
        if (isFree) {
            //cas操作保证金钱操作正确
            AtomicFloat atomicFloat = new AtomicFloat(0l);
            //配置cpu核数的收费规则
            float cpuCharge = hardware.getCpuCoreNumber() * hardware.getCpuCoreNumberMoney();
            //网数收费
            float netWorkSpeedCharge = hardware.getNetworkSpeed() * hardware.getNetworkSpeedMoney();
            //cpu类型的收费
            float cpuTypeCharge = Integer.parseInt(hardware.getCpuType()) * hardware.getCpuTypemoney();
            //磁盘收费
            float diskCharge = hardware.getDisk() * hardware.getDiskMoney();

            //原子操作
            float memoryCount = atomicFloat.addAndGet
                    (cpuCharge + netWorkSpeedCharge + cpuTypeCharge + diskCharge);

            //金额不一致抛出异常
            if (memoryCount != hardware.getMemory()) throw new CustomExpection(500, "金额不正确");

            boolean isSuccess = currentPorxy.addHardwarePacketDP(hardware, memoryCount);
            if (!isSuccess) throw new CustomExpection(500,"购买失败");
            return Result.success(200, "购买成功", hardware);
        }

        //非弹性设置默认
        HardwareDto hardwareDto = new HardwareDto();
        Hardware hard = hardwareService.getById(3);
        if (hard == null) throw new CustomExpection(500, "error");
        Packet packet = getById(5);
        if (packet == null) throw new CustomExpection(500, "error");
        BeanUtils.copyProperties(hard, hardwareDto);
        BeanUtils.copyProperties(packet, hardwareDto);
        return Result.success(200, "success", hardwareDto);
    }

    @Transactional
    public boolean addHardwarePacketDP(HardwareDto hardware, float memoryCount) {
        if (memoryCount != hardware.getMemory()) throw new CustomExpection(500, "金额不正确");
        //硬件资源
        Hardware hardwareDB = new Hardware();
        BeanUtils.copyProperties(hardware, hardwareDB);
        hardwareDB.setCreatedAt(new Date());
        hardwareDB.setMemory(memoryCount);
        hardwareService.save(hardwareDB);
        //创建套餐
        Packet packet = new Packet();
        packet.setHardwareId(hardwareDB.getId()); //设置硬件id
        packet.setDescription("1"); //设置详情信息
        packet.setName("222"); //设置套餐名
        packet.setCreatedAt(new Date());
        save(packet);
        return true;
    }

}
