#include <iostream>
#include "graph.h"
int main() {
  std::cout << "Hello, World!" << std::endl;
  auto graph = readGraphFromFile("test/testData/2/g2a-1.txt");
  return 0;
}
