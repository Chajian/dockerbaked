package com.ibs.dockerbacked.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ContainerEntity {
    String id;
    String imageId;
    long userId;
    String created;
}
