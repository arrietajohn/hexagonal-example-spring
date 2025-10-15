package com.jcaa.hexagonal.core.domin.event;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class DomainEvent {
  private final String eventId;
  private final LocalDateTime occurredOn;

  protected DomainEvent() {
    this.eventId = UUID.randomUUID().toString();
    this.occurredOn = LocalDateTime.now();
  }

  public String getEventId() {
    return eventId;
  }

  public LocalDateTime getOccurredOn() {
    return occurredOn;
  }

  public abstract String getEventType();
}
