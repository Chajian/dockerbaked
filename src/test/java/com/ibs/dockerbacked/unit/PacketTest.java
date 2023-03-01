package com.ibs.dockerbacked.unit;

import com.ibs.dockerbacked.entity.Packet;
import com.ibs.dockerbacked.service.PacketService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PacketTest {
    @Autowired
    private PacketService packetService;

    @Test
    public void PacketAll() {
        System.out.println(packetService.getById(1));
    }

    @Test
    public void PacketInsert() {
        Packet packet = new Packet();
        packet.setDescription("1");
        packet.setHardwareId(2);
        packet.setName("1");
        packetService.save(packet);
    }

    @Test
    public void PacketUpdate() {
        Packet packet = new Packet();
        packet.setId(4);
        packet.setDescription("1");
        packet.setHardwareId(2);
        packetService.saveOrUpdate(packet);
    }

    @Test
    public void PacketRemove() {
        packetService.removeById(4);
    }
}
