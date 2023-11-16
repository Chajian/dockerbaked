package com.ibs.dockerbacked.task.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 事件
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event<T> {
    long eventId;
    String name;
    String desc;
    T t;
}
