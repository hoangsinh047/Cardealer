package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.common.BaseDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ObjectDto extends BaseDTO {
    private Long count;
    private Object data;
}