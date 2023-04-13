//
// Created by krrer on 29.03.23.
//

#include <cmath>
#include "heuristics.h"

uint8_t naive(BoardEncoded board) {
  auto b = decode(board);

  uint16_t eval = 0;

  for (int i = b.size() - 1; i >= 0; --i) {
    if (b.at(i) != i) eval++;
  }

  return eval;
}
uint8_t manhattan(BoardEncoded board) {
  auto b = decode(board);

  uint16_t eval = 0;
  int board_dim = static_cast<int>(std::sqrt(BOARD_SIZE));
  for (int i = b.size()-1; i >= 0; --i) {
    if (b[i] == 0) {
      eval += std::abs(static_cast<int>(i / board_dim - 3));
      eval += std::abs(static_cast<int>(i % board_dim - 3));
    } else {
      eval += std::abs(static_cast<int>(i / board_dim - (b[i] - 1) / board_dim));
      eval += std::abs(static_cast<int>(i % board_dim - (b[i] - 1) % board_dim));
    }
  }

  return eval;
}
