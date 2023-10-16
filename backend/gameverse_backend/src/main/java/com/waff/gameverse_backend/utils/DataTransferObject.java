package com.waff.gameverse_backend.utils;

public interface DataTransferObject<T> extends AbstractDataTransferObject {

    T convertToDto();
}
