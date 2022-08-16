package com.ibs.dockerbacked.data;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class IamgeEntity {
    String id;
    String name;
    String author;
    long userId;
    String created;
}
