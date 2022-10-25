package com.danielbostwick.example;

import java.util.List;

public interface TestFixtures {
  List<String> CMD_INDEX_EXAMPLE_1 = List.of(
      "index", "1", "soup", "tomato", "cream", "salt");
  List<String> CMD_INDEX_EXAMPLE_2 = List.of(
      "index", "2", "cake", "sugar", "eggs", "flour", "sugar", "cocoa", "cream", "butter");
  List<String> CMD_INDEX_EXAMPLE_3 = List.of(
      "index", "1", "bread", "butter", "salt");
  List<String> CMD_INDEX_EXAMPLE_4 = List.of(
      "index", "3", "soup", "fish", "potato", "salt", "pepper");

  List<String> CMD_QUERY_EXAMPLE_1 = List.of("query", "butter");
  List<String> CMD_QUERY_EXAMPLE_2 = List.of("query", "sugar");
  List<String> CMD_QUERY_EXAMPLE_3 = List.of("query", "soup");
  List<String> CMD_QUERY_EXAMPLE_4 = List.of("query", "(butter | potato) & salt");
  List<String> CMD_QUERY_EXAMPLE_5 = List.of("query", "notfound");

}
