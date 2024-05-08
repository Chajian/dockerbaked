package com.ibs.dockerbacked.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import lombok.Data;

/**
 * 套餐信息
 */
@Data
public class PacketVo {
    private int id;
    private String description;
    @TableField("name_p")
    private String name;
    private int hardwareId;
    private Hardware hardware;
    public void toPacketVo(Packet packet){
        this.id = packet.getId();
        this.description = packet.getDescription();
        this.name = packet.getName();
        this.hardwareId = packet.getHardwareId();
    }

    public Packet toPacket(){
        Packet packet = new Packet();
        packet.setName(this.name);
        packet.setId(this.id);
        packet.setHardwareId(this.hardwareId);
        packet.setDescription(this.description);
        return packet;
    }

}
