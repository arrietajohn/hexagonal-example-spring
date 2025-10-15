package com.jcaa.hexagonal.core.domin.exception;

public final class UserAlreadyExistsException extends DomainException {

  private static final String ERROR_MESSAGE_TEMPLATE =
      "Ya existe un usuario registrado con el email: %s";

  public UserAlreadyExistsException(final String email) {
    super(String.format(ERROR_MESSAGE_TEMPLATE, email));
  }
}
