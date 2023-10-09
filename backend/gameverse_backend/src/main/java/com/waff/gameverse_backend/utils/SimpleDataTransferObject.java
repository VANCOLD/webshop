package com.waff.gameverse_backend.utils;

public interface SimpleDataTransferObject<T extends SimpleDto> extends AbstractDataTransferObject {

    T convertToSimpleDto();
}
