package com.danielbostwick.example;

import com.danielbostwick.example.commands.DebugCommand;
import com.danielbostwick.example.commands.ExitCommand;
import com.danielbostwick.example.commands.IndexCommand;
import com.danielbostwick.example.commands.QueryCommand;
import com.danielbostwick.example.errors.InvalidCommmandTokenError;

import java.util.List;

import static com.danielbostwick.example.Utilities.log;

public interface SearchEngineCommand {
  <R extends SearchEngineCommandResult> R apply();

  static SearchEngineCommand createFromTokens(final List<String> tokens) {
    // The first token is the name of the command
    final var commandToken = tokens.get(0);

    log(() -> String.format("parseCommand() - commandToken: '%s'", commandToken));

    if (commandToken.equalsIgnoreCase(ExitCommand.TOKEN)) {
      return new ExitCommand();
    } else if (commandToken.equalsIgnoreCase(IndexCommand.TOKEN)) {
      return IndexCommand.fromTokens(tokens);
    } else if (commandToken.equalsIgnoreCase(QueryCommand.TOKEN)) {
      return QueryCommand.fromTokens(tokens);
    } else if (commandToken.equalsIgnoreCase(DebugCommand.TOKEN)) {
      return new DebugCommand();
    } else {
      throw InvalidCommmandTokenError.of(commandToken);
    }
  }
}
