//
// Created by krrer on 09.03.23.
//
#include <iostream>
#include <filesystem>
#include "graph.h"
#include "graph_algorithms.h"
#include "fmt/format.h"

int main() {
  std::string zad1_path  = "test/testData/1";
  auto func = BreadthFirstSearch;
  for (const auto &test_name : std::filesystem::directory_iterator(zad1_path)) {
    auto graph = readGraphFromFile(test_name.path());
    verticies dfs_order;
    edges dfs_edges;
    std::tie(dfs_order, dfs_edges) = func(*graph);
    std::cout << test_name << std::endl;
    for (const auto &vert : dfs_order) {
      std::cout << vert << "\n";
    }
    for (const auto &[source, dest] : dfs_edges) {
      fmt::print("{} {}\n", source, dest);
    }
  }

  return 0;
}
