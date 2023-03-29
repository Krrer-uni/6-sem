#include <iostream>
#include "solver.h"

int main() {
  BoardEncoded b = getWinningBoard();
  printBoard(b);

    auto a = get_available_moves(b);
  return 0;
}
