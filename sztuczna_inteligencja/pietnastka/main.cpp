#include <iostream>
#include <cassert>
#include "solver.h"

int main() {
  printBoard(getWinningBoard());
  BoardLiteral b = getBoard();
  printBoard(b);

    auto a = solve(b);
  assert(is_valid_solution(a,b)==true);

    for(const auto& move : a){
      std::cout << (uint16_t)move << " ";
    }

  return 0;
}
