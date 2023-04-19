#include <iostream>
#include <cassert>
#include "solver.h"
#include "heuristics.h"
#include <vector>
#include <fstream>

int main() {
  auto k = getBoard(3);
  std::cout << (int)manhattan(encode(k));

  std::vector<int> no_moves_man;
  std::vector<int> no_searched_man;
  std::vector<int> no_moves_in;
  std::vector<int> no_searched_in;

  for(int i = 0 ; i < 20; i++){
    std::ofstream myfile;
    myfile.open ("example.csv", std::ios_base::app);
    std::cout << i << std::endl;
    BoardLiteral b = getBoard();
    if(!is_solvable(b)){
    make_solvable(b);
    }
    auto [moves_man,searched_man] = solve(b, manhattan);
    no_moves_man.push_back(moves_man.size());
    no_searched_man.push_back(searched_man);
    auto [moves_in,searched_in] = solve(b, manhattan_lc);
    no_moves_in.push_back(moves_in.size());
    no_searched_in.push_back(searched_in);
    myfile << moves_man.size() << ","<< searched_man << ","<< moves_in.size() << "," << searched_in << std::endl  ;
    myfile.close();
  }


  return 0;
}
