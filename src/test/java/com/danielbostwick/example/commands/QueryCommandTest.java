package com.danielbostwick.example.commands;

import com.danielbostwick.example.storage.SearchEngineInMemoryStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static com.danielbostwick.example.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryCommandTest {

  private SearchEngineInMemoryStorage storage;

  @BeforeEach
  void setupEach() {
    storage = new SearchEngineInMemoryStorage();

    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_1, storage).apply();
    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_2, storage).apply();
    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_3, storage).apply();
    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_4, storage).apply();
  }

  @Test
  void queryReturnsValidResults() {
    final var queryResult1 = QueryCommand.fromTokens(CMD_QUERY_EXAMPLE_1, storage).apply();
    assertEquals(Set.of(2, 1), queryResult1.documentIds);

    final var queryResult2 = QueryCommand.fromTokens(CMD_QUERY_EXAMPLE_2, storage).apply();
    assertEquals(Set.of(2), queryResult2.documentIds);

    final var queryResult3 = QueryCommand.fromTokens(CMD_QUERY_EXAMPLE_3, storage).apply();
    assertEquals(Set.of(3), queryResult3.documentIds);

//    final var queryResult4 = QueryCommand.fromTokens(CMD_QUERY_EXAMPLE_4, storage).apply();
//    assertEquals(Set.of(1, 3), queryResult4.documentIds);

    final var queryResult5 = QueryCommand.fromTokens(CMD_QUERY_EXAMPLE_5, storage).apply();
    assertEquals(Set.of(), queryResult5.documentIds);
  }
}
