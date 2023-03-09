#include <iostream>
#include "graph.h"
#include "graph_algorithms.h"
#include "fmt/format.h"

int main() {
  std::cout << "Hello, World!" << std::endl;
  auto graph = readGraphFromFile("test/testData/2/g2a-1.txt");
  verticies dfs_order;
  edges dfs_edges;
  std::tie(dfs_order,dfs_edges) = DeepFirstSearch(*graph);
  for(const auto& vert : dfs_order ){
    std::cout << vert << "\n" ;
  }
  for(const auto& [source,dest] : dfs_edges){
    fmt::print("{} {}\n ", source,dest);
  }
  return 0;
}
