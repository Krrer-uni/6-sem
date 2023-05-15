#include <iostream>
#include "include/fileread.h"
#include "include/algorithms.h"

int main(int argc, char *argv[]) {
  std::string input, output, sources;
  std::shared_ptr<SsSources> ss_sources{};
  std::shared_ptr<P2pSsources> p2p_sources{};
  bool is_p2p = false;
  bool is_ss = false;
  for (int i = 1; i < argc; i++) {
    auto x = argv[i];
    if (strcmp(argv[i], "-d") == 0) {
      i += 1;
      input = std::string(argv[i]);
      continue;
    }
    if (strcmp(argv[i], "-ss") == 0) {
      is_ss = true;
      sources = argv[++i];
      continue;
    }
    if (strcmp(argv[i], "-p2p") == 0) {
      is_p2p = true;
      sources = argv[++i];
      continue;
    }
    if (strcmp(argv[i], "-oss") == 0 || strcmp(argv[i], "-op2p") == 0) {
      output = argv[++i];
      continue;
    }
  }

  auto graph = Filereader::readGraph(input);
  std::vector<algorithms::dijstra_return_data> results;
  if(is_ss){
    ss_sources = Filereader::readSSfile(sources);
    for(const auto & source : ss_sources->verticies){
      algorithms::PriorityQueue* pq = new algorithms::DialPriorityQueue(1000);
      algorithms::PriorityQueue* dpq = new algorithms::StdPriorityQueue();
      algorithms::PriorityQueue* rhpq = new algorithms::RadixHeap(20,20);
      algorithms::PriorityQueue* bh = new algorithms::BinaryHeap(7);

      auto result = algorithms::runDijsktra(*graph, source,0,bh);
      results.push_back(result);
      std::cout << "source: " + std::to_string(source) + " " << result.toString() << std::endl;
      delete pq;
    }
  } else if(is_p2p){
    p2p_sources = Filereader::readP2Pfile(sources);
  }else{
    std::cerr << "Sources not declared\n";
    return -1;
  }


  std::cout << "Hello, World!" << std::endl;
  return 0;
}
