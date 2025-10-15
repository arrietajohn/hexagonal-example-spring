package com.jcaa.hexagonal.core.domin.exception;

public final class InvalidUserIdException extends DomainException {

  public InvalidUserIdException(final String message) {
    super(message);
  }
}
