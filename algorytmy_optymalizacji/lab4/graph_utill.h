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
#include <map>

typedef std::pair<int,int> edge_data; // cost flow

struct edge{
  int cost;
  int flow;
  bool is_back_edge = false;
  bool operator<(const edge& a) const{
    return cost < a.cost;
  }
};

typedef std::vector<std::map<size_t,edge>> graph_edges;

class Graph{
  const size_t verticies_size;
  bool isDirected;


 public:
  int max_edge;
  int min_edge;
  graph_edges verticies;
  Graph(size_t verticies_size, bool isDirected);
  void addEdge(size_t source, size_t dest, int cost, bool is_backedge);
  size_t getSize() const;


};

int hamming_size(int,int);
int zero_size(int,int);
int hamming_width(int,int);

Graph generate_cube(int dim);
Graph generate_bipartite(int k, int i);
#endif //LAB1__GRAPH_H_
