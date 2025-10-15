package com.jcaa.hexagonal.core.domin.exception;

public abstract class DomainException extends RuntimeException {

  protected DomainException(final String message) {
    super(message);
  }

  protected DomainException(final String message, final Throwable cause) {
    super(message, cause);
  }
}
