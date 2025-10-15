package com.jcaa.hexagonal.core.domin;

import com.jcaa.hexagonal.core.domin.event.DomainEvent;
import com.jcaa.hexagonal.core.domin.event.LoginFailedEvent;
import com.jcaa.hexagonal.core.domin.event.UserLoggedInEvent;
import com.jcaa.hexagonal.core.domin.valueobject.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class BitacoraLogin {

  private static final String SUCCESS_MESSAGE = "Login exitoso";
  private static final String ERROR_NULL_MESSAGE = "El mensaje no puede ser nulo o vacío";
  private static final String ERROR_EXISTS_ID = "La bitácora ya tiene un ID asignado";
  private static final boolean LOGIN_SUCCESS = true;
  private static final boolean LOGIN_FAILED = false;

  private Long id;
  private final UserId userId;
  private final LocalDateTime fecha;
  private final boolean exito;
  private final String mensaje;
  private final List<DomainEvent> domainEvents;

  // Constructor privado
  private BitacoraLogin(
      final Long id,
      final UserId userId,
      final LocalDateTime fecha,
      final boolean exito,
      final String mensaje) {
    this.id = id;
    this.userId = userId;
    this.fecha = Objects.nonNull(fecha) ? fecha : LocalDateTime.now();
    this.exito = exito;
    this.mensaje = validateMessage(mensaje);
    this.domainEvents = new ArrayList<>();
  }

  /** Factory method para registrar un login exitoso */
  public static BitacoraLogin recordSuccessfulLogin(final UserId userId, final String userEmail) {
    final LocalDateTime now = LocalDateTime.now();
    final BitacoraLogin bitacora =
        new BitacoraLogin(null, userId, now, LOGIN_SUCCESS, SUCCESS_MESSAGE);

    // Disparar evento de dominio
    bitacora.recordEvent(
        new UserLoggedInEvent(userId.value(), userEmail, LOGIN_SUCCESS));

    return bitacora;
  }

  /** Factory method para registrar un login fallido */
  public static BitacoraLogin recordFailedLogin(
      final String userEmail, final String errorMessage) {
    final LocalDateTime now = LocalDateTime.now();
    final BitacoraLogin bitacora =
        new BitacoraLogin(null, null, now, LOGIN_FAILED, errorMessage);

    // Disparar evento de dominio
    bitacora.recordEvent(new LoginFailedEvent(userEmail, errorMessage));

    return bitacora;
  }

  /** Factory method para login fallido con userId conocido */
  public static BitacoraLogin recordFailedLoginWithUserId(
      final UserId userId, final String userEmail, final String errorMessage) {
    final LocalDateTime now = LocalDateTime.now();
    final BitacoraLogin bitacora = new BitacoraLogin(null, userId, now, LOGIN_FAILED, errorMessage);

    // Disparar evento de dominio
    bitacora.recordEvent(new LoginFailedEvent(userEmail, errorMessage));

    return bitacora;
  }

  /** Factory method para reconstruir desde persistencia */
  public static BitacoraLogin reconstituteFromPersistence(
      final Long id,
      final Long userId,
      final LocalDateTime fecha,
      final boolean exito,
      final String mensaje) {

    return new BitacoraLogin(
        id, Objects.nonNull(userId) ? new UserId(userId) : null, fecha, exito, mensaje);
  }

  /** Método de negocio: asignar ID después de la persistencia */
  public void assignId(final Long id) {
    if (Objects.nonNull(this.id)) {
      throw new IllegalStateException(ERROR_EXISTS_ID);
    }
    this.id = id;
  }

  // Gestión de eventos de dominio
  public void recordEvent(final DomainEvent event) {
    this.domainEvents.add(event);
  }

  public List<DomainEvent> getDomainEvents() {
    return Collections.unmodifiableList(domainEvents);
  }

  public void clearDomainEvents() {
    this.domainEvents.clear();
  }

  @Override
  public boolean equals(final Object o) {
    if (this == o) return true;
    if (Objects.isNull(o) || getClass() != o.getClass()) return false;
    final BitacoraLogin that = (BitacoraLogin) o;
    return Objects.equals(id, that.id)
        && Objects.equals(userId, that.userId)
        && Objects.equals(fecha, that.fecha);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, fecha);
  }

  @Override
  public String toString() {
    return "BitacoraLogin{"
        + "id="
        + id
        + ", userId="
        + userId
        + ", fecha="
        + fecha
        + ", exito="
        + exito
        + ", mensaje='"
        + mensaje
        + '\''
        + '}';
  }

  // Getters
  public Long getId() {
    return id;
  }

  public UserId getUserId() {
    return userId;
  }

  public LocalDateTime getFecha() {
    return fecha;
  }

  public boolean isExito() {
    return exito;
  }

  public String getMensaje() {
    return mensaje;
  }

  // Validaciones privadas
  private static String validateMessage(final String mensaje) {
    if (Objects.isNull(mensaje) || mensaje.isBlank()) {
      throw new IllegalArgumentException(ERROR_NULL_MESSAGE);
    }
    return mensaje.trim();
  }


}
