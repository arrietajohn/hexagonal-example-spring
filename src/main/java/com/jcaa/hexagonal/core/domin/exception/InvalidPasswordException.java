package com.jcaa.hexagonal.core.domin.exception;

public final class InvalidPasswordException extends DomainException {

  public InvalidPasswordException(final String message) {
    super(message);
  }
}
