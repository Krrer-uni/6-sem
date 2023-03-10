
//
// Created by krrer on 09.03.23.
//
#include <iostream>
#include <filesystem>
#include "graph.h"
#include "graph_algorithms.h"
#include "fmt/format.h"

int main() {
  std::string zad1_path  = "test/testData/3";
  for (const auto &test_path : std::filesystem::directory_iterator(zad1_path)) {
    std::string test_name = test_path.path();
    auto graph = readGraphFromFile(test_name);
    std::vector<std::vector<size_t>> strongly_connected_components;
    strongly_connected_components= StronglyConnectedComponent(*graph);
    std::cout << test_name << std::endl;
    if(graph->getSize() < 200) {
      for (const auto &component : strongly_connected_components) {
        for (const auto &vert : component) {
          std::cout << vert << " ";
        }
        std::cout << std::endl;
      }
    }
  }

  return 0;
}