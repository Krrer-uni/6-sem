#include <iostream>
#include "solver.h"

int main() {
  auto b = getWinningBoard();
  printBoard(b);
  auto x = get_available_moves(b);
  return 0;
}
