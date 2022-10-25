package com.danielbostwick.example.commands;

import com.danielbostwick.example.SearchEngineCommand;
import com.danielbostwick.example.SearchEngineServices;
import com.danielbostwick.example.SearchEngineStorage;
import com.danielbostwick.example.models.QueryExpression;
import com.danielbostwick.example.results.QueryResult;

import java.util.List;

import static com.danielbostwick.example.Utilities.log;

public class QueryCommand implements SearchEngineCommand {
  public static final String TOKEN = "query";

  public static QueryCommand fromTokens(final List<String> commandTokens) {
    return fromTokens(commandTokens, SearchEngineServices.storageEngine());
  }

  public static QueryCommand fromTokens(final List<String> commandTokens,
                                        final SearchEngineStorage storage) {
    log(() -> String.format("QueryCommand.fromTokens() - commandTokens:%s", commandTokens));
    final var queryExpression = String.join("", commandTokens.subList(1, commandTokens.size()));

    return new QueryCommand(QueryExpression.parse(queryExpression), storage);
  }

  public final QueryExpression searchExpression;
  public final SearchEngineStorage storage;

  private QueryCommand(final QueryExpression searchExpression,
                       final SearchEngineStorage storage) {
    this.searchExpression = searchExpression;
    this.storage = storage;
  }

  @Override
  public QueryResult apply() {
    log(() -> String.format("QueryCommand#apply - expression: '%s'", searchExpression));

    return new QueryResult(storage.matchDocuments(searchExpression));
  }
}
