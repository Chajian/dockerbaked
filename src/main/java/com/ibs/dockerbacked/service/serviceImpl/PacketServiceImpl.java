package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Order;
import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.execption.CustomExpection;
import com.ibs.dockerbacked.mapper.PacketMapper;
import com.ibs.dockerbacked.service.HardwareService;
import com.ibs.dockerbacked.service.PacketService;
import com.mysql.cj.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author sn
 */
@Slf4j
@Service
public class PacketServiceImpl extends ServiceImpl<PacketMapper, Packet> implements PacketService {
    @Autowired
    private HardwareService hardwareService;

    @Override
    @Transient
    @RequiresRoles("admin")
    public Result<Boolean> createPacket(Hardware hardware, boolean isFree) {
        //判断是否是否是管理员 todo

        //判断全部字段是否按照条件来填
        checkParam(hardware);

        //弹性收费
        if (isFree) {
            //配置cpu核数的收费规则
            int cpuCharge = hardware.getCpuCoreNumber() * 20;
            //网数收费
            int netWorkSpeedCharge = hardware.getNetworkSpeed() * 20;
            //cpu类型的收费
            int cpuTypeCharge = Integer.parseInt(hardware.getCpuType()) * 20;
            //磁盘收费
            int diskCharge = hardware.getDisk() * 20;
            int memoryCount = cpuCharge + netWorkSpeedCharge + cpuTypeCharge + diskCharge;
            //硬件资源
            Hardware hardwareDB = new Hardware();
            BeanUtils.copyProperties(hardware, hardwareDB);
            hardwareDB.setCreatedAt(new Date());
            hardwareDB.setMemory(memoryCount);
            hardwareService.save(hardwareDB);
            //创建套餐
            Packet packet = new Packet();
            packet.setHardwareId(hardwareDB.getId());
            packet.setDescription("1");
            packet.setName("222");
            packet.setCreatedAt(new Date());
            save(packet);
        }
        return Result.success(200,"success",true);
    }

    private void checkParam(Hardware hardware) {
        if (hardware.getCpuType() == null || hardware.getCpuType().equals("")) {
            throw new CustomExpection(500, "硬件类型不能为空");
        }
        if (hardware.getCpuCoreNumber() <=0) {
            throw new CustomExpection(500, "cpu核心数不能小于等于0");
        }
        if (hardware.getNetworkSpeed() <=0) {
            throw new CustomExpection(500, "网络参数不能小于等于0");
        }
        if (hardware.getMemory() <=0) {
            throw new CustomExpection(500, "收费金额不能小于等于0");
        }
        if (hardware.getDisk() <= 0) {
            throw new CustomExpection(500, "");
        }
    }
}
