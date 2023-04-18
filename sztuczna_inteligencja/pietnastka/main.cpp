#include <iostream>
#include <cassert>
#include "solver.h"
#include "heuristics.h"
int main() {
  BoardLiteral b = getBoard();
//  BoardEncoded d = encode(b);
//  BoardLiteral b =  {1,  2,  3,  4,
//  9,  5,  7,  8,
//  0, 6, 10,  11,
//  13, 14, 15, 12};
//
//BoardEncoded d = 853860024066471105;
//BoardLiteral b = decode(d);
  printBoard(b);
  if(!is_solvable(b)){
//    make_solvable(b);
    std::cout << "nie da się rozwiązać\n";
    return 0;
  }
  auto [moves,searched] = solve(b, manhattan);
  assert(is_valid_solution(moves,b)==true);
    for(const auto& move : moves){
      std::cout << (uint16_t)move << " ";
    }
  std::cout <<  "\n";
  std::cout << moves.size() << " " << searched << "\n";

//  std::tie(moves,searched) = solve(b, manhattan);
//  std::cout << moves.size() << " " << searched << "\n";


  return 0;
}
