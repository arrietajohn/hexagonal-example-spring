package com.jcaa.hexagonal.core.domin.event;

public final class UserRegisteredEvent extends DomainEvent {

  private static final String EVENT_TYPE = "USER_REGISTERED";

  private final Long userId;
  private final String email;
  private final String name;

  public UserRegisteredEvent(final Long userId, final String email, final String name) {
    super();
    this.userId = userId;
    this.email = email;
    this.name = name;
  }

  public Long getUserId() {
    return userId;
  }

  public String getEmail() {
    return email;
  }

  public String getName() {
    return name;
  }

  @Override
  public String getEventType() {
    return EVENT_TYPE;
  }

  @Override
  public String toString() {
    return "UserRegisteredEvent{"
        + "userId="
        + userId
        + ", email='"
        + email
        + '\''
        + ", name='"
        + name
        + '\''
        + ", occurredOn="
        + getOccurredOn()
        + '}';
  }
}
