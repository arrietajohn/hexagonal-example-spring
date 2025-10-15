package com.jcaa.hexagonal.core.domin.valueobject;

import com.jcaa.hexagonal.core.domin.exception.InvalidPasswordException;
import java.util.Objects;

public record Password(String value) {

  private static final int MIN_LENGTH = 4;
  private static final String ERROR_NULL_OR_EMPTY = "La contraseña no puede estar vacía";
  private static final String ERROR_MIN_LENGTH = "La contraseña debe tener al menos %d caracteres";
  private static final String MASKED_VALUE = "********";

  public Password {
    if (Objects.isNull(value) || value.isBlank()) {
      throw new InvalidPasswordException(ERROR_NULL_OR_EMPTY);
    }
    if (value.length() < MIN_LENGTH) {
      throw new InvalidPasswordException(String.format(ERROR_MIN_LENGTH, MIN_LENGTH));
    }
  }

  public String getValue() {
    return value;
  }

  @Override
  public String toString() {
    return MASKED_VALUE;
  }
}
