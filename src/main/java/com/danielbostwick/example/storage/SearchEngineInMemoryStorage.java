package com.danielbostwick.example.storage;

import com.danielbostwick.example.SearchEngineStorage;
import com.danielbostwick.example.models.QueryExpression;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.danielbostwick.example.Utilities.log;

public class SearchEngineInMemoryStorage implements SearchEngineStorage {

  private final Map<Integer, Set<String>> documentToTokens = new HashMap<>();

  private final Map<String, Set<Integer>> tokenToDocuments = new HashMap<>();

  @Override
  public void index(final int docId, final List<String> tokens) {
    if (documentExists(docId)) {
      removeDocument(docId);
    }

    // Store document with tokens
    documentToTokens.put(docId, new HashSet<>(tokens));

    // Store tokens to documents
    for (final var token : tokens) {
      if (tokenToDocuments.containsKey(token)) {
        final var tokenDocuments = tokenToDocuments.get(token);
        tokenDocuments.add(docId);
        tokenToDocuments.put(token, tokenDocuments);
      } else {
        tokenToDocuments.put(token, new HashSet<>(Set.of(docId)));
      }
      final var documentSet = tokenToDocuments.get(token);
      documentSet.add(docId);
      tokenToDocuments.replace(token, documentSet);
    }

    log(() -> String.format("SearchEngineInMemoryStorage#index - documentToTokens:\n%s", documentToTokens));
    log(() -> String.format("SearchEngineInMemoryStorage#index - tokenToDocuments:\n%s", tokenToDocuments));
  }

  @Override
  public int documentCount() {
    return documentToTokens.keySet().size();
  }

  @Override
  public boolean documentExists(final int docId) {
    return documentToTokens.containsKey(docId);
  }

  @Override
  public void removeDocument(final int docId) {
    tokenToDocuments.forEach((token, documentSet) -> documentSet.remove(docId));
    documentToTokens.remove(docId);
  }

  @Override
  public Set<Integer> matchDocuments(final QueryExpression expression) {
    log(() -> String.format("SearchEngineInMemoryStorage#matchDocuments - expression:'%s'", expression));
    log(() -> String.format("SearchEngineInMemoryStorage#matchDocuments - tokensToDocuments:\n%s", tokenToDocuments));


    return expression.matches(tokenToDocuments);
  }
}
