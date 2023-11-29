package com.ibs.dockerbacked.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.Container;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author sn
 */
@Slf4j
@Service
public class PacketServiceImpl extends ServiceImpl<PacketMapper, Packet> implements PacketService {
    @Autowired
    @Lazy
    private HardwareService hardwareService;
    @Autowired
    private PacketMapper packetMapper;
    @Lazy
    @Autowired
    private PacketService currentPorxy;


    @Override
    public Result<HardwareDto> createPacket(HardwareDto hardware, boolean isFree) {

        //弹性收费
        if (isFree) {
            //cas操作保证金钱操作正确
            AtomicFloat atomicFloat = new AtomicFloat(0l);
            List<Float> count = new ArrayList<>();
            //配置cpu核数的收费规则
            float cpuCharge = hardware.getCpuCoreNumber() * hardware.getCpuCoreNumberMoney();
            count.add(cpuCharge);
            //网数收费
            float netWorkSpeedCharge = hardware.getNetworkSpeed() * hardware.getNetworkSpeedMoney();
            count.add(netWorkSpeedCharge);
            //cpu类型的收费
            float cpuTypeCharge = Integer.parseInt(hardware.getCpuType()) * hardware.getCpuTypemoney();
            count.add(cpuTypeCharge);
            //磁盘收费
            float diskCharge = hardware.getDisk() * hardware.getDiskMoney();
            count.add(diskCharge);
            //内存收费
            float memoryCharge = hardware.getMemory() * hardware.getMemory();
            count.add(memoryCharge);
            //原子操作
            count.forEach(money -> {
                AtomicFloat atomic = new AtomicFloat(0l);
                atomicFloat.addAndGet(atomic.addAndGet(money));
            });

            //套餐资源
            boolean isSuccess = currentPorxy.addHardwarePacketDP(hardware, isFree, atomicFloat.get());
            if (!isSuccess) throw new CustomExpection(500, "购买失败");
            return Result.success(200, "购买成功", hardware);
        }

        //非弹性设置默认
        //查询硬件
        HardwareDto hardwareDto = new HardwareDto();
        Hardware hard = hardwareService.getById(3);
        if (hard == null) throw new CustomExpection(500, "error");
        BeanUtils.copyProperties(hard, hardwareDto);
        //资源
        boolean isSuccess = currentPorxy.addHardwarePacketDP(hardware, isFree, hard.getMoney());
        if (!isSuccess) throw new CustomExpection(500, "购买失败");
        return Result.success(200, "success", hardwareDto);
    }

    @Transactional
    public boolean addHardwarePacketDP(HardwareDto hardware, boolean isFree, float money) {
        //硬件资源
        Hardware hardwareDB = new Hardware();
        BeanUtils.copyProperties(hardware, hardwareDB, "id");
        hardwareDB.setCreatedAt(new Date());
        hardwareDB.setMoney(money);
        packetMapper.addGetId(hardwareDB);
        //创建套餐
        Packet packet = new Packet();
        if (isFree) {
            packet.setDescription("1"); //设置详情信息
            packet.setName("222"); //设置套餐名
        } else {
            //查询套餐
            Packet packetDefalut = getById(4);
            if (packetDefalut == null) throw new CustomExpection(500, "error");
            BeanUtils.copyProperties(packetDefalut,packet,"id");
        }
        packet.setHardwareId(hardwareDB.getId()); //设置硬件id
        packet.setCreatedAt(new Date());
        save(packet);
        return true;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<Packet> getPackets(int page, int size) {
        Page<Packet> p = new Page<>(page, size);
        LambdaQueryWrapper<Packet> lambdaQueryWrapper = new LambdaQueryWrapper<>();//条件
        Page<Packet> pageResult = page(p, lambdaQueryWrapper);
        List<Packet> containers = pageResult.getRecords();
        if (pageResult.getSize() <= 0) {
            return null;
        }
        return containers;
    }
}
