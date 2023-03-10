
//
// Created by krrer on 09.03.23.
//
#include <iostream>
#include <filesystem>
#include "graph.h"
#include "graph_algorithms.h"
#include "fmt/format.h"

int main() {
  std::string zad1_path = "test/testData/4";
  for (const auto &test_path : std::filesystem::directory_iterator(zad1_path)) {
    std::string test_name = test_path.path();
    auto graph = readGraphFromFile(test_name);
    verticies setA, setB;
    bool is_bipartite;
    std::tie(setA, setB, is_bipartite) = isBipartite(*graph);
    std::cout << test_name << std::endl;
    if(!is_bipartite){
      std::cout << "Graph is not bipartite\n";
    }else{
      if (graph->getSize() < 200) {
        for (const auto &vert : setA) {
          std::cout << vert << " ";
        }
        std::cout << std::endl;
        for (const auto &vert : setB) {
          std::cout << vert << " ";
        }
        std::cout << std::endl;

      }
    }
  }

  return 0;
}