package com.jcaa.hexagonal.core.domin.exception;

public final class InvalidCredentialsException extends DomainException {

  private static final String ERROR_MESSAGE =
      "Credenciales inválidas. Email o contraseña incorrectos";

  public InvalidCredentialsException() {
    super(ERROR_MESSAGE);
  }
}
