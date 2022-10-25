package com.danielbostwick.example;

import com.danielbostwick.example.models.QueryExpression;

import java.util.List;
import java.util.Set;

public interface SearchEngineStorage {
  void index(int docId, List<String> tokens);

  int documentCount();

  boolean documentExists(int docId);

  void removeDocument(int docId);

  Set<Integer> matchDocuments(QueryExpression expression);
}
