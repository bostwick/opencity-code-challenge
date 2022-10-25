package com.danielbostwick.example.commands;

import com.danielbostwick.example.SearchEngineCommand;
import com.danielbostwick.example.SearchEngineCommandResult;

import static com.danielbostwick.example.Utilities.log;

public class ExitCommand implements SearchEngineCommand {
  public static final String TOKEN = "exit";

  @Override
  public SearchEngineCommandResult apply() {
    log(() -> "ExitCommand#apply - exiting");
    System.exit(0);

    return new SearchEngineCommandResult() {
      // Unreachable code;
    };
  }
}
