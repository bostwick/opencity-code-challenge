package com.danielbostwick.example;

public final class SearchEngineApp {
  public static void main(final String[] args) {
    SearchEngineServices.initialize();
    new SearchEngineRepl().run();
  }
}
