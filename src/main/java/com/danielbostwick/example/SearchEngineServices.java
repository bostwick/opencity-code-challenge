package com.danielbostwick.example;

import com.danielbostwick.example.storage.SearchEngineInMemoryStorage;

public final class SearchEngineServices {
  private static SearchEngineServices INSTANCE;

  public static void initialize() {
    INSTANCE = new SearchEngineServices();
  }

  private final SearchEngineStorage storageEngine;

  private boolean isDebug = false;

  private SearchEngineServices() {
    this.storageEngine = new SearchEngineInMemoryStorage();
  }

  public static SearchEngineStorage storageEngine() {
    return INSTANCE.storageEngine;
  }

  public static boolean isDebug() {
    return INSTANCE.isDebug;
  }

  public static boolean toggleDebug() {
    INSTANCE.isDebug = !INSTANCE.isDebug;
    return INSTANCE.isDebug;
  }

  public static boolean isInitialized() {
    return INSTANCE != null;
  }
}
