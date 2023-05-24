package com.ibs.dockerbacked.entity.dto;

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
}
