package com.ibs.dockerbacked.entity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.ibs.dockerbacked.entity.Hardware;
import com.ibs.dockerbacked.entity.Packet;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 套餐信息
 */
@ApiModel(value = "套餐参数")
@Data
public class PacketVo {
    private int id;
    private String description;
    @ApiModelProperty(value = "套餐名称")
    @TableField("name_p")
    private String name;
    @ApiModelProperty(value = "套餐硬件参数Id")
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
