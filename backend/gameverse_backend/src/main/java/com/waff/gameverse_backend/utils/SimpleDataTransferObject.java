package com.waff.gameverse_backend.utils;

public interface SimpleDataTransferObject<T extends SimpleDto> {

    T convertToSimpleDto();
}
