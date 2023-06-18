#include <iostream>
#include <valarray>
#include <chrono>
#include <numeric>
#include "graph_utill.h"
#include "bitset"
#include "solver.h"

int main() {
  int tries = 10;
  std::vector<double> results_time;
  std::vector<double> results_flow;

  for (int k = 1; k <= 16; k++) {
    std::vector<double> k_results_time;
    std::vector<double> k_results_flow;
    std::cout << "k = " << k;
    for (int j = 0; j < tries; j++) {
      auto g = generate_cube(k);
      auto start = std::chrono::high_resolution_clock::now();
      auto res = run_edmonds_karp(g, 0, std::pow(2,k)- 1);
      auto stop = std::chrono::high_resolution_clock::now();
      auto time =
          std::chrono::duration_cast<std::chrono::microseconds>(stop - start).count() / 1000000.0;

      k_results_flow.push_back(res);
      k_results_time.push_back(time);
    }
    auto flow_sum = std::accumulate(k_results_flow.begin(), k_results_flow.end(), 0.0);
    double time_sum = std::accumulate(k_results_time.begin(), k_results_time.end(), 0.0);
    results_flow.push_back(flow_sum / k_results_flow.size());
    results_time.push_back(time_sum / k_results_time.size());
    std::cout << "\n";
  }

  std::ofstream opFile;
  opFile.open("cube_output.wtf");

  for (const auto &k : results_time) {
      opFile << k << " ";
    opFile << std::endl;
  }
  for (const auto &k : results_flow) {
    opFile << k << " ";
    opFile << std::endl;
  }
  opFile.close();
//  std::cout << res << " " << time << std::endl;

  return 0;
}
