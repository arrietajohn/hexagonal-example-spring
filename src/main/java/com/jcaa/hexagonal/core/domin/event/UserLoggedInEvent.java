package com.jcaa.hexagonal.core.domin.event;

public final class UserLoggedInEvent extends DomainEvent {

  private static final String EVENT_TYPE = "USER_LOGGED_IN";

  private final Long userId;
  private final String email;
  private final boolean successful;

  public UserLoggedInEvent(final Long userId, final String email, final boolean successful) {
    super();
    this.userId = userId;
    this.email = email;
    this.successful = successful;
  }

  public Long getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public boolean isSuccessful() {
    return successful;
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }

  @Override
  public String toString() {
    return "UserLoggedInEvent{"
        + "userId="
        + userId
        + ", email='"
        + email
        + '\''
        + ", successful="
        + successful
        + ", occurredOn="
        + getOccurredOn()
        + '}';
  }
}
