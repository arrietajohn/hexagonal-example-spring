package com.jcaa.hexagonal.core.domin.event;

public final class LoginFailedEvent extends DomainEvent {

  private static final String EVENT_TYPE = "LOGIN_FAILED";

  private final String email;
  private final String reason;

  public LoginFailedEvent(final String email, final String reason) {
    super();
    this.email = email;
    this.reason = reason;
  }

  public String getEmail() {
    return email;
  }

  public String getReason() {
    return reason;
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }

  @Override
  public String toString() {
    return "LoginFailedEvent{"
        + "email='"
        + email
        + '\''
        + ", reason='"
        + reason
        + '\''
        + ", occurredOn="
        + getOccurredOn()
        + '}';
  }
}
