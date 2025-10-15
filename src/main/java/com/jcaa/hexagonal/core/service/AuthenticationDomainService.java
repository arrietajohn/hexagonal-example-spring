package com.jcaa.hexagonal.core.service;

import com.jcaa.hexagonal.core.domin.BitacoraLogin;
import com.jcaa.hexagonal.core.domin.User;
import com.jcaa.hexagonal.core.domin.exception.InvalidCredentialsException;

import java.util.Objects;

/**
 * Servicio de dominio para lógica de autenticación
 * En DDD, los servicios de dominio contienen lógica que no pertenece naturalmente a una sola entidad
 */
public final class AuthenticationDomainService {

  private static final String ERROR_NULL_USER = "El usuario no puede ser nulo";
  private static final String ERROR_NULL_PASSWORD = "La contraseña no puede ser nula o vacía";
  private static final String ERROR_NULL_EMAIL = "El email no puede ser nulo o vacío";
  private static final String ERROR_NULL_REASON = "La razón no puede ser nula o vacía";

  private AuthenticationDomainService() {
    // Prevenir instanciación
  }

  /**
   * Lógica de negocio: Autenticar un usuario y registrar el intento en bitácora
   *
   * @param user Usuario a autenticar
   * @param rawPassword Contraseña en texto plano
   * @return BitacoraLogin con el resultado del intento
   * @throws InvalidCredentialsException si las credenciales son inválidas
   */
  public static BitacoraLogin authenticate(final User user, final String rawPassword) {
    Objects.requireNonNull(user, ERROR_NULL_USER);
    if (Objects.isNull(rawPassword) || rawPassword.isBlank()) {
      throw new IllegalArgumentException(ERROR_NULL_PASSWORD);
    }

    // Verificar credenciales
    if (user.verifyPassword(rawPassword)) {
      // Login exitoso - crear bitácora y disparar evento
      return BitacoraLogin.recordSuccessfulLogin(
          user.getId(),
          user.getEmail().value()
      );
    } else {
      // Login fallido - crear bitácora y disparar evento
      BitacoraLogin.recordFailedLoginWithUserId(
          user.getId(),
          user.getEmail().value(),
          new InvalidCredentialsException().getMessage()
      );

      // Lanzar excepción de dominio
      throw new InvalidCredentialsException();
    }
  }

  /**
   * Registrar intento de login fallido cuando el usuario no existe
   */
  public static BitacoraLogin recordFailedLoginAttempt(final String email, final String reason) {
    if (Objects.isNull(email) || email.isBlank()) {
      throw new IllegalArgumentException(ERROR_NULL_EMAIL);
    }
    if (Objects.isNull(reason) || reason.isBlank()) {
      throw new IllegalArgumentException(ERROR_NULL_REASON);
    }

    return BitacoraLogin.recordFailedLogin(email, reason);
  }
}
