#include <iostream>
#include <valarray>
#include <chrono>
#include <numeric>
#include "graph_utill.h"
#include "bitset"
#include "solver.h"

int main() {
  int tries = 100;
  std::vector<std::vector<double>> results_time;
  std::vector<std::vector<double>> results_flow;

  for (int k = 3; k <= 10; k++) {
    std::vector<double> k_results_time;
    std::vector<double> k_results_flow;
    std::cout << "k = " << k << " j = ";
    for (int i = 1; i <= k; i++) {
      std::vector<double> i_results_time;
      std::vector<double> i_results_flow;
      std::cout << i << ", ";

      for (int j = 0; j < tries; j++) {
        auto g = generate_bipartite(k, i);
        auto start = std::chrono::high_resolution_clock::now();
        auto res = run_edmonds_karp(g, 0, 1);
        auto stop = std::chrono::high_resolution_clock::now();
        auto time =
            std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1000000.0;

        i_results_flow.push_back(res);
        i_results_time.push_back(time);
      }
      auto flow_sum = std::accumulate(i_results_flow.begin(),i_results_flow.end(),0.0);
      auto time_sum = std::accumulate(i_results_time.begin(),i_results_time.end(),0.0);
      k_results_flow.push_back(flow_sum/i_results_flow.size());
      k_results_time.push_back(time_sum/i_results_time.size());
    }
    std::cout << "\n";

    results_flow.push_back(k_results_flow);
    results_time.push_back(k_results_time);
  }


  std::ofstream opFile;
  opFile.open("bipartite_output.wtf");

  for(const auto& k : results_time){
    for(const auto& i : k){
      opFile << i << " ";
    }
    opFile << std::endl;
  }
  for(const auto& k : results_flow){
    for(const auto& i : k){
      opFile << i << " ";
    }
    opFile << std::endl;
  }
  opFile.close();
//  std::cout << res << " " << time << std::endl;

  return 0;
}
