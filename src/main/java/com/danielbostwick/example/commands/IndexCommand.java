package com.danielbostwick.example.commands;

import com.danielbostwick.example.SearchEngineCommand;
import com.danielbostwick.example.SearchEngineServices;
import com.danielbostwick.example.SearchEngineStorage;
import com.danielbostwick.example.Utilities;
import com.danielbostwick.example.errors.InvalidIndexCommandError;
import com.danielbostwick.example.results.IndexResult;

import java.util.List;

import static com.danielbostwick.example.Utilities.guard;
import static com.danielbostwick.example.Utilities.log;

public class IndexCommand implements SearchEngineCommand {

  public static final String TOKEN = "index";

  public static IndexCommand fromTokens(final List<String> commandTokens) {
    return fromTokens(commandTokens, SearchEngineServices.storageEngine());
  }

  public static IndexCommand fromTokens(final List<String> commandTokens,
                                        final SearchEngineStorage storage) {

    guard(commandTokens.size() == 0, InvalidIndexCommandError::invalidCommandToken);
    guard(commandTokens.size() == 1, InvalidIndexCommandError::missingDocId);
    guard(!commandTokens.get(1).matches(Utilities.REGEX_INTEGER), InvalidIndexCommandError::invalidDocId);

    for (int i = 2; i < commandTokens.size(); i++) {
      final var token = commandTokens.get(i);
      guard(!token.matches(Utilities.REGEX_ALPHANUMERIC_TOKEN), () -> InvalidIndexCommandError.invalidTokenError(token));
    }

    final var docId = Integer.valueOf(commandTokens.get(1));
    final var docTokens = commandTokens.subList(2, commandTokens.size());

    return new IndexCommand(docId, docTokens, storage);
  }

  public final Integer docId;
  public final List<String> tokens;

  public final SearchEngineStorage storage;

  private IndexCommand(final Integer docId,
                       final List<String> tokens,
                       final SearchEngineStorage storage) {
    this.docId = docId;
    this.tokens = tokens;
    this.storage = storage;
  }

  @Override
  public IndexResult apply() {
    log(() -> String.format("IndexCommand#apply - docId: %d tokens: %s", docId, tokens));
    storage.index(docId, tokens);
    return new IndexResult(docId);
  }
}
