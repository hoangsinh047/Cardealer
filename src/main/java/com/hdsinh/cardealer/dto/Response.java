package com.hdsinh.cardealer.dto;

import com.hdsinh.cardealer.common.BaseDTO;
import com.hdsinh.cardealer.common.ReturnCode;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Response<T> extends BaseDTO {
    private int code;
    private String status;
    private String msg;
    private Long totalCount;
    private Object data;
    private Long lastTimeUploaded;
    public boolean isSuccess() {
        return ReturnCode.SUCCESS.getValue() == code;
    }
}

