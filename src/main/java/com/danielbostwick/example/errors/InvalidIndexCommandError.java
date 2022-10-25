package com.danielbostwick.example.errors;

public class InvalidIndexCommandError extends RuntimeException {
  public static InvalidIndexCommandError invalidCommandToken() {
    return new InvalidIndexCommandError("invalid command");
  }

  public static InvalidIndexCommandError missingDocId() {
    return new InvalidIndexCommandError("missing doc-id");
  }

  public static InvalidIndexCommandError invalidDocId() {
    return new InvalidIndexCommandError("invalid doc-id. doc-id must be positive integer");
  }

  public static InvalidIndexCommandError invalidTokenError(final String token) {
    return new InvalidIndexCommandError(String.format("invalid token '%s'", token));
  }

  private InvalidIndexCommandError(final String message) {
    super("index error " + message);
  }
}
