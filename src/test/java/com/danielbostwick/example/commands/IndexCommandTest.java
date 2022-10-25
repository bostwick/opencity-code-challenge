package com.danielbostwick.example.commands;

import com.danielbostwick.example.SearchEngineServices;
import com.danielbostwick.example.errors.InvalidIndexCommandError;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.danielbostwick.example.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.*;

class IndexCommandTest {


  @BeforeAll
  static void setup() {
    SearchEngineServices.initialize();
  }

  @Test
  void IndexCommand_fromTokens_parsesValidInput() {
    final var command1 = IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_1);

    assertEquals(1, command1.docId);
    assertEquals(List.of("soup", "tomato", "cream", "salt"), command1.tokens);

    final var command2 = IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_2);

    assertEquals(2, command2.docId);
    assertEquals(List.of("cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter"), command2.tokens);

    final var command3 = IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_3);

    assertEquals(1, command3.docId);
    assertEquals(List.of("bread", "butter", "salt"), command3.tokens);

    final var command4 = IndexCommand.fromTokens(CMD_INDEX_EXAMPLE_4);

    assertEquals(3, command4.docId);
    assertEquals(List.of("soup", "fish", "potato", "salt", "pepper"), command4.tokens);
  }

  @Test
  void IndexCommand_fromTokens_throwsWhenMissingTokens() {
    assertThrows(InvalidIndexCommandError.class, () -> IndexCommand.fromTokens(List.of()));
    assertThrows(InvalidIndexCommandError.class, () -> IndexCommand.fromTokens(List.of("index")));
    assertThrows(InvalidIndexCommandError.class, () -> IndexCommand.fromTokens(List.of("index", "one")));
    assertThrows(InvalidIndexCommandError.class, () -> IndexCommand.fromTokens(List.of("index", "1", "")));
    assertThrows(InvalidIndexCommandError.class, () -> IndexCommand.fromTokens(List.of("index", "1", "!@#$")));
  }
}
