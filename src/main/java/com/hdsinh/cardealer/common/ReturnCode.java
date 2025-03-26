package com.hdsinh.cardealer.common;

public enum ReturnCode {

    SUCCESS(1, "SUCCESS"),
    ERROR(2, "SYSTEM ERROR");
    private final int value;
    private final String status;
    private ReturnCode(final int value, final String status) {
        this.value = value;
        this.status = status;
    }

    public int getValue() {
        return this.value;
    }

    public String getStatus() {
        return this.status;
    }
}
