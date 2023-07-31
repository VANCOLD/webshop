package com.waff.gameverse_backend.enums;

public enum Status {
    ONGOING("ONGOING"),ABORTED("ABORTED"),COMPLETED("COMPLETED"), TERMINÀTED("TERMINÀTED"), IN_TRANSACTION("IN_TRANSACTION");

    private final String status;

    private Status(String status) {
        this.status = status;
    }

    public Status getStatus(String status) {
        return Status.valueOf(status);
    }
}
