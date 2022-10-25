package com.danielbostwick.example;

public interface Utilities {

  String REGEX_INTEGER = "^\\d+$";
  String REGEX_ALPHANUMERIC_TOKEN = "^[a-zA-Z0-9]+$";

  static void guard(final boolean expression,
                    final ErrorFactory errorFactory) {
    if (expression) {
      throw errorFactory.create();
    }
  }

  static void log(final DebugProducer producer) {
    if (!SearchEngineServices.isInitialized() || SearchEngineServices.isDebug()) {
      System.out.println(producer.apply());
    }
  }

  @FunctionalInterface
  interface DebugProducer {
    String apply();
  }

  @FunctionalInterface
  interface ErrorFactory {
    RuntimeException create();
  }
}
