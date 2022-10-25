package com.danielbostwick.example.commands;

import com.danielbostwick.example.SearchEngineCommand;
import com.danielbostwick.example.SearchEngineCommandResult;
import com.danielbostwick.example.SearchEngineServices;

public class DebugCommand implements SearchEngineCommand {
  public static final String TOKEN = "debug";

  @Override
  public SearchEngineCommandResult apply() {
    SearchEngineServices.toggleDebug();

    return new SearchEngineCommandResult() {
      @Override
      public String toString() {
        return TOKEN + " " + SearchEngineServices.isDebug();
      }
    };
  }
}
