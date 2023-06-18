//
// Created by krrer on 18.06.23.
//
#include <deque>
#include "solver.h"



int run_edmonds_karp(Graph& graph, size_t source, size_t goal) {
  auto *queue = new std::deque<size_t>;
  queue->push_front(source);

  int flow = 0;
  std::vector<int> previous(graph.verticies.size(), -1);

  do {
    previous = std::vector(graph.verticies.size(), -1);
    queue->push_front(source);
    while (!queue->empty()) {
      auto vert = queue->front();
      queue->pop_front();
//      struct
//      {
//        bool operator()(std::pair<const size_t, edge> a, std::pair<const size_t, edge> b) const { return a.second.cost < b.second.cost; }
//      }mapLess;
//
//      std::vector<std::pair< size_t, edge>> edges;
//      for(const auto & a : (graph.verticies[vert])){
//        edges.emplace_back(a);
//      }
//      std::sort(edges.begin(),edges.end(),mapLess);
      for (auto &edge : graph.verticies[vert]) {
        if (previous[edge.first] == -1 && edge.first != source && edge.second.cost - edge.second.flow > 0) {
          previous[edge.first] = vert;
          queue->push_back(edge.first);
        }
        if(edge.first == goal){
          break;
        }
      }
    }

    if (previous[goal] != -1) {
      auto df = INT32_MAX;
      for (auto i = goal; previous[i] != -1; i = previous[i]) {
        df = std::min(df, graph.verticies[previous[i]][i].cost - graph.verticies[previous[i]][i].flow);
      }
      flow += df;
      for (auto i = goal; previous[i] != -1; i = previous[i]) {
        if(!graph.verticies[previous[i]][i].is_back_edge){
          graph.verticies[previous[i]][i].flow += df;
          graph.verticies[i][previous[i]].cost += df;
        }else{
          graph.verticies[previous[i]][i].cost -= df;
          graph.verticies[i][previous[i]].flow -= df;
        }
      }
    }

  } while (previous[goal] != -1);

  return flow;
};