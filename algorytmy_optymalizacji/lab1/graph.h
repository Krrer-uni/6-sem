//
// Created by krrer on 07.03.23.
//

#ifndef LAB1__GRAPH_H_
#define LAB1__GRAPH_H_

#include <array>
#include <list>
#include <vector>
#include <memory>
#include <fstream>
#include <iostream>
#include <boost/algorithm/string/split.hpp>
#include <boost/algorithm/string/classification.hpp>

class Graph{
  const size_t verticies_size;
  bool isDirected;

 public:
  std::vector<std::list<size_t>> verticies;
  Graph(size_t verticies_size, bool isDirected);
  void addEdge(size_t source, size_t dest);
  size_t getSize() const;
  std::vector<std::list<size_t>> transpose() const;
};

std::shared_ptr<Graph> readGraphFromFile(std::string filename);

#endif //LAB1__GRAPH_H_
