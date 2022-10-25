package com.danielbostwick.example.errors;

public class InvalidCommmandTokenError extends RuntimeException {

  public final String token;

  public static InvalidCommmandTokenError of(final String commandToken) {
    return new InvalidCommmandTokenError(commandToken);
  }

  private InvalidCommmandTokenError(final String token) {
    this.token = token;
  }
}
