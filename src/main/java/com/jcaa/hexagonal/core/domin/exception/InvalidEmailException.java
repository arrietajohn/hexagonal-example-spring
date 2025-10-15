package com.jcaa.hexagonal.core.domin.exception;

public final class InvalidEmailException extends DomainException {

  public InvalidEmailException(final String message) {
    super(message);
  }
}
