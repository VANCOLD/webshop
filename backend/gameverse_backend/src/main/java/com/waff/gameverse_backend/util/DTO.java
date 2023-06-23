package com.waff.gameverse_backend.util;

public interface DTO<T,I> {
    T convertToDto(I object);
}
