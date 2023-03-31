//
// Created by krrer on 29.03.23.
//

#include "heuristics.h"


uint naive(BoardEncoded board){
  auto b = decode(board);

  uint16_t eval = 0;

  for(int i = b.size()-1; i >= 0; --i){
    if(b.at(i) != i) eval++;
  }

  return eval;
}
uint manhattan(BoardEncoded board) {
  auto b = decode(board);

  uint16_t eval = 0;

  for(int i = b.size()-1; i >= 0; --i){
    uint x = std::abs(i - b[i]);
    eval += x / BOARD_EDGE;
    eval += x % BOARD_EDGE;
  }

  return eval;
}
