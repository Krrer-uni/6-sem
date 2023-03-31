#include <iostream>
#include <cassert>
#include "solver.h"
#include "heuristics.h"
int main() {
  BoardLiteral b = getBoard();
  printBoard(b);
  if(!is_solvable(b)){
    make_solvable(b);
  }
//  assert(is_valid_solution(a,b)==true);
//    for(const auto& move : a){
//      std::cout << (uint16_t)move << " ";
//    }
  auto [moves,searched] = solve(b, manhattan);
  std::cout << moves.size() << " " << searched << "\n";

//  std::tie(moves,searched) = solve(b, manhattan);
//  std::cout << moves.size() << " " << searched << "\n";


  return 0;
}
