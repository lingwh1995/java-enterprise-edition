package org.bluebridge._05_channel_handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lingwh
 * @desc Student
 * @date 2025/9/24 15:30
 */

@AllArgsConstructor
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;

}