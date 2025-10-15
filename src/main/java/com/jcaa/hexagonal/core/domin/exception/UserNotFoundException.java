package com.jcaa.hexagonal.core.domin.exception;

public final class UserNotFoundException extends DomainException {

  private static final String ERROR_MESSAGE_BY_EMAIL_TEMPLATE =
      "Usuario no encontrado con el email: %s";
  private static final String ERROR_MESSAGE_BY_ID_TEMPLATE = "Usuario no encontrado con el ID: %s";

  public UserNotFoundException(final String email) {
    super(String.format(ERROR_MESSAGE_BY_EMAIL_TEMPLATE, email));
  }

  public UserNotFoundException(final Long id) {
    super(String.format(ERROR_MESSAGE_BY_ID_TEMPLATE, id));
  }
}
