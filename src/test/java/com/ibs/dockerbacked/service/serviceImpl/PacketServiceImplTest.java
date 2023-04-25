package com.ibs.dockerbacked.service.serviceImpl;

import com.ibs.dockerbacked.DockerCloudBoot;
import com.ibs.dockerbacked.common.Result;
import com.ibs.dockerbacked.entity.dto.HardwareDto;
import com.ibs.dockerbacked.service.PacketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DockerCloudBoot.class)
public class PacketServiceImplTest {
    @Autowired
    private PacketService packetService;

    @Test
   public void createPacket() {
        HardwareDto hardwareDto = new HardwareDto();
        hardwareDto.setCpuCoreNumber(20);
        hardwareDto.setCpuType("R7-500");
        hardwareDto.setNetworkSpeed(20);
        hardwareDto.setDisk(20);
        hardwareDto.setCreatedAt(new Date());
        hardwareDto.setCpuCoreNumberMoney(20);
        hardwareDto.setDiskMoney(20);
        hardwareDto.setNetworkSpeedMoney(20);
        hardwareDto.setMemory(1200);
        Result<HardwareDto> packet = packetService.createPacket(hardwareDto, true);

    }
}
