//
// Created by krrer on 07.03.23.
//

#include <cmath>
#include <random>
#include "graph_utill.h"

Graph::Graph(size_t verticies_size, bool isDirected) :
    verticies_size(verticies_size),
    verticies(verticies_size + 1),
    isDirected(isDirected),
    max_edge(INT32_MIN),
    min_edge(INT32_MAX) {}

void Graph::addEdge(size_t source, size_t dest, int cost, bool is_backedge) {
  verticies[source][dest] = {cost, 0, is_backedge};
  if (!isDirected) {
    verticies[dest][source] = {cost, 0};
  }
}

size_t Graph::getSize() const {
  return verticies_size;
}

int zero_size(int n, int k) {
  int i = (k - 1);
  int s = 0;
  while (i >= 0) {
    if ((n & (1 << i)) == 0) {
      s++;
    }
    i--;
  }
  return s;
}

int hamming_size(int n, int k) {
  int s = 0;
  int i = 1 << (k - 1);
  while (i > 0) {
    if ((n & i) != 0) {
      s++;
    }
    i = i >> 1;
  }
  return s;
}

Graph generate_cube(int dim) {
  int size = std::pow(2, dim);
  auto cube = Graph(size, true);

  std::random_device rd;
  std::mt19937 gen(rd());
  for (int a = 0; a < cube.getSize(); a++) {
    for (int i = 0; i < dim; ++i) {
      if ((a & (1 << i)) == 0) {
        int n = a + (1 << i);
        int u = std::max(hamming_size(n, dim),
                         std::max(zero_size(n, dim),
                                  std::max(hamming_size(a, dim), zero_size(a, dim))));
        std::uniform_int_distribution<int> dist(1, (int) std::pow(2.0, u));
        int c = dist(gen);
        cube.addEdge(a, n, c, false);
        cube.addEdge(n, a, 0, true);
      }
    }
  }
  return cube;
}

Graph generate_bipartite(int k, int n) {
  int s = std::pow(2, k);
  int size = 2 * s + 2;
  auto bipartite = Graph(size, true);
  std::random_device rd;
  std::mt19937 gen(rd());
  std::uniform_int_distribution<int> dist(2 + s, 1 + 2 * s);
  for (int i = 2; i < s + 2; i++) {
    bipartite.addEdge(i, 0, 0, true);
    bipartite.addEdge(0, i, 1, false);
  }

  for (int i = s+2; i < size; i++) {
    bipartite.addEdge(i, 1, 1, false);
    bipartite.addEdge(1, i, 0, true);
  }

  for (int i = 2; i < s + 2; i++) {
    for (int j = 0; j < n; j++) {
      int neighbour;
      do {
        neighbour = dist(gen);
      } while (bipartite.verticies[i].contains(neighbour));
      bipartite.addEdge(i, neighbour, 1, false);
      bipartite.addEdge(neighbour, i, 0, true);
    }
  }

  return bipartite;
};
