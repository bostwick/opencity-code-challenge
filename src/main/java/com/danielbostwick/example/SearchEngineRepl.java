package com.danielbostwick.example;

import com.danielbostwick.example.errors.InvalidCommmandTokenError;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import static com.danielbostwick.example.Utilities.log;

public final class SearchEngineRepl {

  private final PrintStream stdout = System.out;
  private final Scanner stdinScanner = new Scanner(System.in);

  @SuppressWarnings("TextBlockMigration")
  private static final String WELCOME_BANNER =
      ("Welcome to the OpenCity Coding Challenge Search Engine\n" +
       "\n" +
       "Available Commands:\n" +
       "    index [doc id] [tokens...]      Index a new document in the search engine\n" +
       "    query [query expression]        Query for documents matching the expression\n" +
       "    debug                           Toggle debug mode\n" +
       "    exit                            exit the search engine\n");

  public SearchEngineRepl() {
  }

  public void run() {
    stdout.println(WELCOME_BANNER);

    //noinspection InfiniteLoopStatement
    while (true) {
      stdout.print("Search Engine> ");
      final var tokens = readTokens();

      log(() -> String.format("Read tokens: %s%n", tokens));

      try {
        final var command = SearchEngineCommand.createFromTokens(tokens);
        final var result = command.apply();
        stdout.println(result);
      } catch (RuntimeException err) {
        stdout.println(err.getMessage());
      }
    }
  }

  private List<String> readTokens() {
    final var tokens = new ArrayList<String>();
    final var tokenizer = new StringTokenizer(stdinScanner.nextLine());

    while (tokenizer.hasMoreElements()) {
      tokens.add(tokenizer.nextToken());
    }

    return tokens;
  }
}
