//
// Created by krrer on 29.03.23.
//

#include "heuristics.h"


uint16_t naive(BoardEncoded board){
  auto b = decode(board);

  uint16_t eval = 0;

  for(int i = b.size()-1; i >= 0; --i){
    if(b.at(i) == i) eval++;
  }

  return eval;
}
