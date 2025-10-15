package com.jcaa.hexagonal.core.domin;

import com.jcaa.hexagonal.core.domin.event.DomainEvent;
import com.jcaa.hexagonal.core.domin.event.UserRegisteredEvent;
import com.jcaa.hexagonal.core.domin.valueobject.Email;
import com.jcaa.hexagonal.core.domin.valueobject.Password;
import com.jcaa.hexagonal.core.domin.valueobject.UserId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class User {

  private static final String DEFAULT_ROLE = "customer";
  private static final String DEFAULT_AVATAR = "https://picsum.photos/800";
  private static final String ERROR_NULL_NAME = "El nombre no puede ser nulo o vacío";
  private static final String ERROR_NULL_EMAIL = "El email no puede ser nulo";
  private static final String ERROR_NULL_PASSWORD = "La contraseña no puede ser nula";
  private static final String ERROR_EXISTS_USER = "El usuario ya tiene un ID asignado";
  private UserId id;
  private final String name;
  private final Email email;
  private final Password password;
  private final String avatar;
  private final String role;
  private final LocalDateTime createdAt;
  private final List<DomainEvent> domainEvents;

  private User(
      final UserId id,
      final String name,
      final Email email,
      final Password password,
      final String avatar,
      final String role,
      final LocalDateTime createdAt) {
    this.id = id;
    this.name = validateName(name);
    this.email = Objects.requireNonNull(email, ERROR_NULL_EMAIL);
    this.password = Objects.requireNonNull(password, ERROR_NULL_PASSWORD);
    this.avatar = Objects.nonNull(avatar) && !avatar.isBlank() ? avatar : DEFAULT_AVATAR;
    this.role = Objects.nonNull(role) && !role.isBlank() ? role : DEFAULT_ROLE;
    this.createdAt = Objects.nonNull(createdAt) ? createdAt : LocalDateTime.now();
    this.domainEvents = new ArrayList<>();
  }

  /** Factory method para registrar un nuevo usuario (caso de uso: registro) */
  public static User register(
      final String name, final String email, final String password, final String avatar) {

    final User user =
        new User(
            null,
            name,
            new Email(email),
            new Password(password),
            avatar,
            DEFAULT_ROLE,
            LocalDateTime.now());

    // Disparar evento de dominio (userId será null hasta persistir)
    user.recordEvent(new UserRegisteredEvent(null, user.email.value(), user.name));

    return user;
  }

  /** Factory method para reconstruir un usuario existente desde persistencia */
  public static User reconstituteFromPersistence(
      final Long id,
      final String name,
      final String email,
      final String password,
      final String avatar,
      final String role,
      final LocalDateTime createdAt) {

    return new User(
        new UserId(id), name, new Email(email), new Password(password), avatar, role, createdAt);
  }

  /** Factory method para reconstruir desde API externa (Platzi) */
  public static User fromExternalApi(
      final Long id,
      final String name,
      final String email,
      final String password,
      final String avatar,
      final String role) {

    return new User(
        new UserId(id),
        name,
        new Email(email),
        new Password(password),
        avatar,
        role,
        LocalDateTime.now());
  }

  /** Método de negocio: asignar ID después de la persistencia */
  public void assignId(final Long id) {
    if (Objects.nonNull(this.id)) {
      throw new IllegalStateException(ERROR_EXISTS_USER);
    }
    this.id = new UserId(id);
  }

  /** Método de negocio: verificar credenciales */
  public boolean verifyPassword(final String rawPassword) {
    return this.password.value().equals(rawPassword);
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
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
    final User user = (User) o;
    return Objects.equals(email, user.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(email);
  }

  @Override
  public String toString() {
    return "User{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", email="
        + email
        + ", role='"
        + role
        + '\''
        + ", createdAt="
        + createdAt
        + '}';
  }

  // Getters
  public UserId getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Email getEmail() {
    return email;
  }

  public Password getPassword() {
    return password;
  }

  public String getAvatar() {
    return avatar;
  }

  public String getRole() {
    return role;
  }

  // Validaciones privadas
  private static String validateName(final String name) {
    if (Objects.isNull(name) || name.isBlank()) {
      throw new IllegalArgumentException(ERROR_NULL_NAME);
    }
    return name.trim();
  }
}
