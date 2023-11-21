package com.ibs.dockerbacked.entity.dto;

import com.ibs.dockerbacked.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddOrder {
    int packetId;
    long userId;
    AddContainer addContainer;
    int lifeTime;
    Order order;

    public AddOrder(int packetId, long userId, AddContainer addContainer, int lifeTime) {
        this.packetId = packetId;
        this.userId = userId;
        this.addContainer = addContainer;
        this.lifeTime = lifeTime;
    }
}
