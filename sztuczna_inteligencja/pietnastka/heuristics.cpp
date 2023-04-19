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
      continue;
    } else {
      eval += std::abs(static_cast<int>(i / board_dim - (b[i] - 1) / board_dim));
      eval += std::abs(static_cast<int>(i % board_dim - (b[i] - 1) % board_dim));
    }
  }

  return eval;
}

uint8_t manhattan_lc(BoardEncoded board) {
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

  for (int i = 0; i < board_dim; i++){
    for(int k = i; k < b.size(); k+=board_dim){
      for(int j = k+board_dim; j < b.size(); j+=board_dim){
        if(b[k] == 0 || b[j] == 0) continue;
        if((b[k]-1)%board_dim == k%board_dim  && (b[j]-1)%board_dim == j%board_dim && (b[k]-1) > (b[j]-1)) eval+=2;
      }
    }
  }
  for (int i = 0; i < b.size(); i+=board_dim){
    for(int k = i; k < i+board_dim; k++){
      for(int j = k+1; j < i+board_dim; j++){
        if(b[k] == 0 || b[j] == 0) continue;
        if((b[k]-1)/board_dim == k/board_dim && (b[j]-1)/board_dim == j/board_dim && (b[k]-1) > (b[j]-1)) eval+=2;
      }
    }
  }
  return eval;
}
