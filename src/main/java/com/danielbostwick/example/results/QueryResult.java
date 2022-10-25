package com.danielbostwick.example.models;

import java.util.List;

public class QueryResult {
  public final List<Integer> documentIds;

  public QueryResult(final List<Integer> documentIds) {
    this.documentIds = documentIds;
  }
}
