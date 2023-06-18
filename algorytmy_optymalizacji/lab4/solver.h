//
// Created by krrer on 18.06.23.
//

#ifndef LAB4__SOLVER_H_
#define LAB4__SOLVER_H_
#include "graph_utill.h"

struct dijstra_return_data {
  size_t verticies;
  size_t edges;
  int lowest_cost;
  int highest_cost;
  double time;
  int path;
  size_t source = 0;
  size_t dest = 0;
  std::string toString() {
    return "verticies: [" + std::to_string(verticies) + "] edges: [" + std::to_string(edges) +
        "] lowest: [" + std::to_string(lowest_cost) + "] highest: [" + std::to_string(highest_cost)
        + "]";
  }
};


int run_edmonds_karp(Graph &graph, size_t source, size_t goal);


#endif //LAB4__SOLVER_H_
