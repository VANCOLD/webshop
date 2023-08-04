package com.waff.gameverse_backend.Utils;

import java.io.Serializable;

public interface DataTransferObject<T> extends Serializable {

  public T convertToDto();
}
