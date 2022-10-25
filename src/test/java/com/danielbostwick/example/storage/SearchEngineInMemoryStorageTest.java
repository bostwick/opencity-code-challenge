package com.danielbostwick.example.storage;

import com.danielbostwick.example.commands.IndexCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.danielbostwick.example.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchEngineInMemoryStorageTest {

  private SearchEngineInMemoryStorage storage;

  @BeforeEach
  void setupEach() {
    storage = new SearchEngineInMemoryStorage();
  }

  @Test
  void indexingDocumentsIncreasesDocumentCount() {
    assertEquals(0, storage.documentCount());

    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_1, storage).apply();
    assertEquals(1, storage.documentCount());

    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_2, storage).apply();
    assertEquals(2, storage.documentCount());

    // Re-indexes docId 1
    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_3, storage).apply();
    assertEquals(2, storage.documentCount());

    IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_4, storage).apply();
    assertEquals(3, storage.documentCount());
  }
}
