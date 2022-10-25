package com.danielbostwick.example.models;

import com.danielbostwick.example.errors.InvalidQueryCommandError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.danielbostwick.example.Utilities.REGEX_ALPHANUMERIC_TOKEN;
import static com.danielbostwick.example.Utilities.log;

public interface QueryExpression {
  Set<String> tokens();

  Set<Integer> matches(final Set<Map.Entry<String, Set<Integer>>> entries);

  static QueryExpression parse(final String expression) {
    log(() -> String.format("QueryExpression.parse() - expression:'%s'", expression));
    final var expressionTokens = tokenize(expression);
    return parse(expressionTokens);
  }

  static QueryExpression parse(final List<String> expressionTokens) {
    log(() -> String.format("QueryExpression.parse() - expressionTokens:%s", expressionTokens));

    QueryExpression result = null;

    for (int idx = 0; idx < expressionTokens.size(); idx += 1) {
      final var token = expressionTokens.get(idx);
      log(() -> String.format("QueryExpression.parse() - token:'%s'", token));

      if (token.equalsIgnoreCase("(")) {
        final var remainingTokens = expressionTokens.subList(idx, expressionTokens.size());
        final var subExprEndIdx = remainingTokens.indexOf(")") + idx;
        final var subExprTokens = expressionTokens.subList(idx + 1, subExprEndIdx);

        final var subResult = parse(subExprTokens);

        if (result == null) {
          result = subResult;
        } else if (result instanceof QueryExpressionConjunction) {
          result = new QueryExpressionConjunction(((QueryExpressionConjunction) result).left, subResult);
        } else if (result instanceof QueryExpressionDisjunction) {
          result = new QueryExpressionDisjunction(((QueryExpressionDisjunction) result).left, subResult);
        }

        idx = subExprEndIdx;
      } else if (token.matches(REGEX_ALPHANUMERIC_TOKEN)) {
        if (result == null) {
          result = new QueryExpressionToken(token);
        } else if (result instanceof QueryExpressionConjunction) {
          result = new QueryExpressionConjunction(((QueryExpressionConjunction) result).left, new QueryExpressionToken(token));
        } else if (result instanceof QueryExpressionDisjunction) {
          result = new QueryExpressionDisjunction(((QueryExpressionDisjunction) result).left, new QueryExpressionToken(token));
        }
      } else if (token.equalsIgnoreCase("&")) {
        result = new QueryExpressionConjunction(result, null);
      } else if (token.equalsIgnoreCase("|")) {
        result = new QueryExpressionDisjunction(result, null);
      } else {
        throw InvalidQueryCommandError.invalidToken(token);
      }
    }

    return result;
  }

  static List<String> tokenize(final String expression) {
    final var tokens = new ArrayList<String>();
    StringBuilder tokenBuilder = new StringBuilder();

    for (var c : expression.split("")) {
      if ("(".equalsIgnoreCase(c) || ")".equalsIgnoreCase(c) ||
          "&".equalsIgnoreCase(c) || "|".equalsIgnoreCase(c)) {
        if (tokenBuilder.length() > 0) {
          tokens.add(tokenBuilder.toString());
          tokenBuilder = new StringBuilder();
        }
        tokens.add(c);
      } else if (c.matches(REGEX_ALPHANUMERIC_TOKEN)) {
        tokenBuilder.append(c);
      } else if (c.matches("\\s+")) {
        if (tokenBuilder.length() > 0) {
          tokens.add(tokenBuilder.toString());
          tokenBuilder = new StringBuilder();
        }
      }
    }

    if (tokenBuilder.length() > 0) {
      tokens.add(tokenBuilder.toString());
      tokenBuilder = new StringBuilder();
    }

    return tokens;
  }
}

class QueryExpressionToken implements QueryExpression {
  public final String token;

  public QueryExpressionToken(final String token) {
    this.token = token;
  }

  @Override
  public Set<String> tokens() {
    return Set.of(token);
  }

  @Override
  public Set<Integer> matches(final Set<Map.Entry<String, Set<Integer>>> entries) {
    return entries.stream()
        .filter(entry -> entry.getKey().equalsIgnoreCase(this.token))
        .flatMap(entry -> entry.getValue().stream())
        .collect(Collectors.toSet());
  }

  @Override
  public String toString() {
    return this.token;
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof QueryExpressionToken
           && ((QueryExpressionToken) obj).token.equalsIgnoreCase(this.token);
  }
}

class QueryExpressionConjunction implements QueryExpression {
  public final QueryExpression left;
  public final QueryExpression right;

  public QueryExpressionConjunction(final QueryExpression left,
                                    final QueryExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public Set<String> tokens() {
    final var result = new HashSet<>(left.tokens());
    result.addAll(right.tokens());
    return result;
  }

  @Override
  public Set<Integer> matches(final Set<Map.Entry<String, Set<Integer>>> entries) {
    final var leftMatches = left.matches(entries);
    final var rightMatches = right.matches(entries);

    final var result = new HashSet<>(leftMatches);
    result.retainAll(rightMatches);
    return result;
  }

  @Override
  public String toString() {
    return String.join("&", this.tokens());
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof QueryExpressionConjunction
           && ((QueryExpressionConjunction) obj).left.equals(this.left)
           && ((QueryExpressionConjunction) obj).right.equals(this.right);
  }
}

class QueryExpressionDisjunction implements QueryExpression {
  public final QueryExpression left;
  public final QueryExpression right;

  public QueryExpressionDisjunction(final QueryExpression left,
                                    final QueryExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public Set<String> tokens() {
    final var result = new HashSet<>(left.tokens());
    result.addAll(right.tokens());
    return result;
  }

  @Override
  public Set<Integer> matches(Set<Map.Entry<String, Set<Integer>>> entries) {
    final var leftMatches = left.matches(entries);
    final var rightMatches = right.matches(entries);

    final var result = new HashSet<>(leftMatches);
    result.addAll(rightMatches);
    return result;
  }

  @Override
  public String toString() {
    return String.join("|", this.tokens());
  }

  @Override
  public boolean equals(Object obj) {
    return obj instanceof QueryExpressionDisjunction
           && ((QueryExpressionDisjunction) obj).left.equals(this.left)
           && ((QueryExpressionDisjunction) obj).right.equals(this.right);
  }
}
