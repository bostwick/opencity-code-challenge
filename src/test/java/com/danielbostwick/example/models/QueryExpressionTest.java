package com.danielbostwick.example.models;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class QueryExpressionTest {

  @Test
  void parse() {
    assertEquals(new QueryExpressionToken("butter"), QueryExpression.parse("butter"));
    assertEquals(new QueryExpressionToken("sugar"), QueryExpression.parse("sugar"));
    assertEquals(
        new QueryExpressionConjunction(new QueryExpressionToken("butter"), new QueryExpressionToken("sugar")),
        QueryExpression.parse("butter & sugar"));
    assertEquals(
        new QueryExpressionDisjunction(new QueryExpressionToken("butter"), new QueryExpressionToken("sugar")),
        QueryExpression.parse("butter | sugar"));
    assertEquals(
        new QueryExpressionConjunction(
            new QueryExpressionDisjunction(new QueryExpressionToken("butter"), new QueryExpressionToken("potato")),
            new QueryExpressionToken("salt")),
        QueryExpression.parse("(butter | potato) & salt"));
    assertEquals(
        new QueryExpressionConjunction(
            new QueryExpressionToken("salt"),
            new QueryExpressionDisjunction(new QueryExpressionToken("butter"), new QueryExpressionToken("potato"))),
        QueryExpression.parse("salt & (butter | potato)"));
  }

  @Test
  void tokenize() {
    assertEquals(List.of("butter"), QueryExpression.tokenize("butter"));
    assertEquals(List.of("sugar"), QueryExpression.tokenize("sugar"));
    assertEquals(List.of("butter", "&", "sugar"), QueryExpression.tokenize("butter & sugar"));
    assertEquals(List.of("butter", "|", "sugar"), QueryExpression.tokenize("butter | sugar"));
    assertEquals(List.of("(", "butter", "|", "potato", ")", "&", "salt"),
        QueryExpression.tokenize("(butter | potato) & salt"));
  }
}
