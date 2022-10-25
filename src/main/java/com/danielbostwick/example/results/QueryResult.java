package com.danielbostwick.example.results;

import com.danielbostwick.example.SearchEngineCommandResult;

import java.util.Set;
import java.util.stream.Collectors;

public class QueryResult implements SearchEngineCommandResult {
  public final Set<Integer> documentIds;

  public QueryResult(final Set<Integer> documentIds) {
    this.documentIds = documentIds;
  }

  @Override
  public String toString() {
    return "query results " + documentIds.stream().map(id -> id.toString()).collect(Collectors.joining(" "));
  }
}
