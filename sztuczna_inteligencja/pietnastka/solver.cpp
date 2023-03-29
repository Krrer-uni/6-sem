//
// Created by krrer on 13.03.23.
//

#include <deque>
#include <random>
#include "solver.h"
#include <boost/iterator/counting_iterator.hpp>
#include <iostream>
#include <iomanip>
#include <unordered_map>
#include <queue>

BoardLiteral decode(BoardEncoded encoded) {
  BoardLiteral board_literal;
  for (auto &cell : board_literal) {
    cell = encoded & 0x000000000000000F;
    encoded = encoded >> 4;
  }
  return board_literal;
}

BoardEncoded encode(BoardLiteral literal) {
  BoardEncoded board_encoded = 0;
  size_t shift = 0;
  for (const auto &cell : literal) {
    board_encoded += (static_cast<uint64_t>(cell) << shift);
    shift += 4;
  }

  return board_encoded;
}

BoardLiteral getBoard() {
  static auto rd = std::random_device{};
  static auto rng = std::default_random_engine{rd()};

  BoardLiteral new_board = BoardLiteral();
  std::copy(boost::counting_iterator(0), boost::counting_iterator(BOARD_SIZE), new_board.begin());
  std::swap(new_board.front(), new_board.back());
  std::shuffle(new_board.begin(), new_board.end() - 1, rng);

  return new_board;
}

void printBoard(BoardLiteral board) {
  auto padding = static_cast<int>(std::log10(BOARD_SIZE));
  for (int i = 0; i < board.size(); i++) {
    std::cout << std::setw(padding + 1) << std::setfill(' ') << board[i] << " ";
    if ((i + 1) % static_cast<int>(std::sqrt(BOARD_SIZE)) == 0) {
      std::cout << "\n";
    }
  }
}

void printBoard(BoardEncoded board) {
  printBoard(decode(board));
}

BoardEncoded getWinningBoard() {
  BoardLiteral new_board = BoardLiteral();
  std::copy(boost::counting_iterator(0), boost::counting_iterator(BOARD_SIZE), new_board.begin());
  std::reverse(new_board.begin(), new_board.end());
  return encode(new_board);
}

std::vector<u_int8_t> solve(BoardLiteral instance) {
  auto
      board_compare = [](std::pair<BoardEncoded, uint16_t> a, std::pair<BoardEncoded, uint16_t> b) {
    return a.second < b.second;
  };
  std::priority_queue<std::pair<BoardEncoded, uint16_t>,
                      std::vector<std::pair<BoardEncoded, uint16_t>>,
                      board_compare> to_be_explored;

  std::unordered_map<BoardEncoded, uint8_t> explored;
  to_be_explored.push_back(encode(instance));
  BoardEncoded winningBoard = getWinningBoard();

  while (!to_be_explored.empty()) {

  }

  std::cout << "COULD NOT FIND SOLUTION\n";

}

// write eval that returns std::tuple<BoardEncoded, uint8_t movetogettoit>
std::vector<std::pair<BoardEncoded, uint8_t>> get_available_moves(BoardEncoded board_encoded) {
  auto board_decoded = decode(board_encoded);
  auto iter_empty = std::find(board_decoded.begin(), board_decoded.end(), 0);
  size_t empty_pos = std::distance(board_decoded.begin(), iter_empty);

  std::vector<std::pair<BoardEncoded, uint8_t>> available_moves;

  size_t board_dim = static_cast<int>(std::sqrt(BOARD_SIZE));

  if (empty_pos >= board_dim) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - board_dim]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - board_dim]);
  }
  if (empty_pos < BOARD_SIZE - board_dim) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + board_dim]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + board_dim]);
  }
  if (empty_pos % board_dim == 0) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + 1]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + 1]);
  }
  if ((empty_pos + 1) % board_dim == 0) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - 1]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - 1]);
  }

  return available_moves;
}