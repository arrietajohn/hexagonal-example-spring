package com.jcaa.hexagonal.core.domin.valueobject;

import com.jcaa.hexagonal.core.domin.exception.InvalidUserIdException;
import java.util.Objects;

public record UserId(Long value) {

  private static final long MIN_VALUE = 0L;
  private static final String ERROR_INVALID_ID = "El ID de usuario debe ser un número positivo";

  public UserId {
    if (Objects.isNull(value) || value <= MIN_VALUE) {
      throw new InvalidUserIdException(ERROR_INVALID_ID);
    }
  }

  public Long getValue() {
    return value;
  }
}
