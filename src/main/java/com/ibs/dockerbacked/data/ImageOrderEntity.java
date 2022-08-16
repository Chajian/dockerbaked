package com.ibs.dockerbacked.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ImageOrderEntity {
    long id;
    String imageId;
    long userId;
    String imageName;
    String created;
    String dockerfile;
}
