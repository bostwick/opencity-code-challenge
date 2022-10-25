package com.danielbostwick.example.errors;

public class InvalidQueryCommandError extends RuntimeException {
  public static InvalidQueryCommandError invalidToken(final String token) {
    return new InvalidQueryCommandError("invalid token " + token);
  }

  private InvalidQueryCommandError(final String message) {
    super("query error " + message);
  }

}
