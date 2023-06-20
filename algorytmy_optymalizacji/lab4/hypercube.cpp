#include <iostream>
#include <valarray>
#include <chrono>
#include <numeric>
#include "graph_utill.h"
#include "bitset"
#include "solver.h"

int main() {
  int dim = 3;
  auto g = generate_bipartite(dim,2);


  std::ofstream opFile;
  opFile.open("gplk_cube.lp");

  opFile << (int)(std::pow(2,dim)) << std::endl;
  opFile << 0 << std::endl;
  opFile << (int)(std::pow(2,dim) -1) << std::endl;
  opFile << g.verticies[0].size() << std::endl;
  for (const auto &k : g.verticies[0]) {
    opFile << k.first << std::endl;
  }

  for (int i = 0; i < g.verticies.size(); i++) {
    for(const auto& x : g.verticies[i]){
      if(x.second.is_back_edge) continue;
      opFile << i << " " << x.first << " " << x.second.cost << std::endl;
    }
  }
  opFile.close();
  for (int a = 0; a < g.getSize(); a++) {
    std::cout << a << " - ";
    for(const auto& e : g.verticies[a]){
      if(e.second.is_back_edge) continue;
      std::cout << "[ " << e.first <<  ", " <<  e.second.flow << "/" << e.second.cost << "]" ;
    }
    std::cout << "\n";
  }
  auto start = std::chrono::high_resolution_clock::now();
  auto res = run_edmonds_karp(g, 0, 1);
  auto stop = std::chrono::high_resolution_clock::now();
  auto time =
      std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1000000.0;

  std::cout << res << " " << time << std::endl;

  return 0;
}
