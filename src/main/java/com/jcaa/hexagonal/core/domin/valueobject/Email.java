package com.jcaa.hexagonal.core.domin.valueobject;

import com.jcaa.hexagonal.core.domin.exception.InvalidEmailException;

import java.util.Objects;
import java.util.regex.Pattern;

public record Email(String value) {

  private static final Pattern EMAIL_PATTERN =
      Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
  private static final String ERROR_NULL_OR_EMPTY = "El email no puede estar vacío";
  private static final String ERROR_INVALID_FORMAT = "El formato del email es inválido: %s";

  public Email {
    if (Objects.isNull(value) || value.isBlank()) {
      throw new InvalidEmailException(ERROR_NULL_OR_EMPTY);
    }
    if (!EMAIL_PATTERN.matcher(value).matches()) {
      throw new InvalidEmailException(String.format(ERROR_INVALID_FORMAT, value));
    }
  }

  public String getValue() {
    return value;
  }
}
