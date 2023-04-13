//
// Created by krrer on 13.03.23.
//

#include <deque>
#include <random>
#include "solver.h"
#include "heuristics.h"
#include <boost/iterator/counting_iterator.hpp>
#include <iostream>
#include <iomanip>
#include <unordered_map>
#include <queue>
#include <list>
#include <set>

static uint find_inversions(BoardLiteral board);
template<class T, class S, class C>
static S &Container(std::priority_queue<T, S, C> &q);
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

BoardLiteral getBoard(unsigned moves) {
  std::random_device rd;     // Only used once to initialise (seed) engine
  std::mt19937 rng(rd());    // Random-number engine used (Mersenne-Twister in this case)

  BoardLiteral backtrack_board = decode(getWinningBoard());
  uint8_t last_move = 16;
  auto pos_empty = std::find(backtrack_board.begin(), backtrack_board.end(), 0);
  for (size_t i = 0; i < moves; i++) {
    auto available_moves = get_available_moves(encode(backtrack_board));
    std::uniform_int_distribution<uint8_t>
        uni(0, available_moves.size() - 1); // Guaranteed unbiased
    auto index = uni(rng);
    auto [board, move] = available_moves[index];
    if (move == last_move)
      move = std::get<1>(available_moves[(index + 1) % available_moves.size()]);
    auto pos_front = std::find(backtrack_board.begin(), backtrack_board.end(), move);
    std::swap(*pos_empty, *pos_front);
    pos_empty = pos_front;
    last_move = move;
  }

  return backtrack_board;
}

void printBoard(BoardLiteral board) {
  auto padding = static_cast<int>(std::log10(BOARD_SIZE));
  for (size_t i = 0; i < board.size(); i++) {
    std::cout << std::setw(padding + 1) << std::setfill(' ')
              << ((board[i] != 0) ? std::to_string(board[i]) : " ") << " ";
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
  std::copy(boost::counting_iterator(1), boost::counting_iterator(BOARD_SIZE), new_board.begin());
  new_board.back() = 0;
  return encode(new_board);
}

std::tuple<std::deque<u_int8_t>, unsigned int> solve(BoardLiteral instance,
                                                     const std::function<uint8_t(BoardEncoded)> &rate_function) {

  class Compare {  // compare class for tuples used in priority queue
   public:
    bool operator()(std::tuple<BoardEncoded, uint8_t, uint8_t, uint8_t> a,
                    std::tuple<BoardEncoded, uint8_t, uint8_t, uint8_t> b) {
      return std::get<1>(a) + std::get<2>(a) > std::get<1>(b) + std::get<2>(b);
    }
  };
  std::priority_queue<std::tuple<BoardEncoded, uint8_t, uint8_t, uint8_t>,
                      std::deque<std::tuple<BoardEncoded, uint8_t, uint8_t, uint8_t>>,
                      Compare> to_be_explored;

  std::unordered_map<BoardEncoded, uint8_t> explored;
  to_be_explored.emplace(std::tuple(encode(instance),
                                    0,
                                    rate_function(encode(instance)),
                                    0));

  BoardEncoded winningBoard = getWinningBoard();
  while (!to_be_explored.empty()) {
//    std::cout << to_be_explored.size() << " " << explored.size() << "\n";
    auto [board, distance, heuristic, last_move] = to_be_explored.top();
    to_be_explored.pop();

    explored.insert({board, last_move});
    auto board_literal = decode(board);
    if (board == winningBoard) {
      return {read_solution(explored, explored.at(board), winningBoard),
              explored.size()};  // return of function
    }

    for (const auto &[state, move] : get_available_moves(board)) {
      if (!explored.contains(state)) {
        uint8_t x = rate_function(state);
        to_be_explored.emplace(state, distance + 1, x, move);
      }
    }
  }

  std::cout << "COULD NOT FIND SOLUTION\n" << explored.size() << "\n"
            << explored.contains(winningBoard) << "\n";

  return {};
}

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
  if (empty_pos % board_dim != 0) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - 1]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos - 1]);
  }
  if ((empty_pos + 1) % board_dim != 0) {
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + 1]);
    available_moves.emplace_back(encode(board_decoded), board_decoded[empty_pos]);
    std::swap(board_decoded[empty_pos], board_decoded[empty_pos + 1]);
  }

  return available_moves;
}

std::deque<uint8_t> read_solution(std::unordered_map<BoardEncoded, uint8_t> &explore_history,
                                  uint8_t last_move,
                                  BoardEncoded winningBoard) {
  std::deque<uint8_t> solution;
  solution.push_front(last_move);
  BoardLiteral backtrack_board = decode(winningBoard);
  auto pos_empty = std::find(backtrack_board.begin(), backtrack_board.end(), 0);
  while (solution.front() != 0) {
    auto pos_front = std::find(backtrack_board.begin(), backtrack_board.end(), solution.front());
    std::swap(*pos_empty, *pos_front);
    solution.push_front(explore_history.at(encode(backtrack_board)));
    pos_empty = pos_front;
  }
  solution.pop_front();
  return solution;
}

bool is_valid_solution(const std::deque<uint8_t> &moves, BoardLiteral instance) {
  BoardLiteral board;
  std::copy(instance.begin(), instance.end(), board.begin());
  auto pos_empty = std::find(board.begin(), board.end(), 0);
  for (const auto &move : moves) {
    auto pos_move = std::find(board.begin(), board.end(), move);
    std::swap(*pos_empty, *pos_move);
    pos_empty = pos_move;
    printBoard(board);
    std::cout << '\n';
  }
  return encode(board) == getWinningBoard();
}
 /// tutaj coś się pierdoli
bool is_solvable(BoardLiteral instance) {
  uint inversions = find_inversions(instance);
  int board_dim = static_cast<int>(std::sqrt(BOARD_SIZE));
  auto zero_pos = std::distance(instance.begin(), std::find(instance.begin(), instance.end(), 0));
  if ((inversions+(board_dim - zero_pos/board_dim)) % 2 == 0) return true;
  return false;
}

void make_solvable(BoardLiteral &board_literal) {
  std::swap(board_literal[0], board_literal[1]);
}

static uint find_inversions(BoardLiteral board) {
  uint inversions = 0;
  for (std::size_t i = 0; i < board.size(); i++) {
    for (std::size_t j = i + 1; j < board.size(); j++) {
      if (board[i] == 0 || board[j] == 0) continue;
      if (board[i] > board[j]) inversions += 1;
    }
  }
  return inversions;
}

template<class T, class S, class C>
static S &Container(std::priority_queue<T, S, C> &q) {
  struct HackedQueue : private std::priority_queue<T, S, C> {
    static S &Container(std::priority_queue<T, S, C> &q) {
      return q.*&HackedQueue::c;
    }
  };
  return HackedQueue::Container(q);
}
