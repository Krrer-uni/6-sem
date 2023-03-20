//
// Created by krrer on 09.03.23.
//
#include <iostream>
#include <filesystem>
#include "graph.h"
#include "graph_algorithms.h"
#include "fmt/format.h"

int main() {
  std::string zad1_path  = "test/testData/2";
  for (const auto &test_path : std::filesystem::directory_iterator(zad1_path)) {
    std::string test_name = test_path.path();
    auto graph = readGraphFromFile(test_name);
    verticies topological_order;
    bool hasCycle;
    std::tie(topological_order, hasCycle) = TopologicalSort(*graph);
    std::cout << test_name << std::endl;
    if(hasCycle){
      std::cout << "ERROR: cycle detected\n";
      continue;
    }
    if(graph->getSize() < 200){
      for (const auto &vert : topological_order) {
        std::cout << vert << "\n";
      }
    }

  }

  return 0;
}