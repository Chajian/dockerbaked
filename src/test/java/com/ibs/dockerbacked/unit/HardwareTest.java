package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.service.HardwareService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author sn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HardwareTest {
    @Autowired
    private HardwareService hardwareService;

    @Test
    public void HardwareAll() {
        System.out.println(hardwareService.getById(2));
    }

    @Test
    public void HardwareInsert() {
        Hardware hardware = new Hardware();
        hardware.setCpuType("1");
        hardware.setCpuCoreNumber(1);
        hardware.setDisk(1);
        hardware.setMemory(100);
        hardware.setNetworkSpeed(12);
        hardwareService.save(hardware);
    }

    @Test
    public void HardwareUpdate() {
        Hardware hardware = new Hardware();
        hardware.setId(2);
        hardware.setCpuType("1");
        hardware.setCpuCoreNumber(2);
        hardwareService.saveOrUpdate(hardware);
    }

    @Test
    public void HardwareRemove() {
        hardwareService.removeById(3);
    }
}
