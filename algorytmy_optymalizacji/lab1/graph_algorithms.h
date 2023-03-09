//
// Created by krrer on 09.03.23.
//

#ifndef LAB1__GRAPH_ALGORITHMS_H_
#define LAB1__GRAPH_ALGORITHMS_H_

#include <vector>
#include "graph.h"

typedef std::vector<size_t> verticies;
typedef std::vector<std::tuple<size_t, size_t>> edges;
std::tuple<std::vector<size_t>, std::vector<std::tuple<size_t, size_t>>> DeepFirstSearch(const  Graph& graph);
std::tuple<verticies, edges> BreadthFirstSearch(const  Graph& graph);
std::tuple<std::vector<size_t>,bool> TopologicalSort(const Graph& graph);
std::vector<std::vector<size_t>> StronglyConnectedComponent(const Graph& graph);
std::tuple<std::vector<size_t>,std::vector<size_t>,bool> isBipartite(const Graph& graph);

#endif //LAB1__GRAPH_ALGORITHMS_H_
