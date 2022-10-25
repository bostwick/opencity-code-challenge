package com.danielbostwick.example.results;

import com.danielbostwick.example.SearchEngineCommandResult;

public class IndexResult implements SearchEngineCommandResult {
  public final int documentId;

  public IndexResult(final int documentId) {
    this.documentId = documentId;
  }

  @Override
  public String toString() {
    return "index ok " + documentId;
  }
}
