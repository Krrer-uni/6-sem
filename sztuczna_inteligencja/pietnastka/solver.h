//
// Created by krrer on 13.03.23.
//

#ifndef PIETNASTKA__SOLVER_H_
#define PIETNASTKA__SOLVER_H_

#include <vector>
#include <array>
#include <unordered_map>
#include <deque>

#define BOARD_SIZE 9

typedef std::array<char8_t, BOARD_SIZE> BoardLiteral;
typedef uint64_t BoardEncoded;

std::deque<u_int8_t> solve(BoardLiteral instance);
BoardLiteral decode(BoardEncoded encoded);
BoardEncoded encode(BoardLiteral literal);
BoardLiteral getBoard();
void printBoard(BoardLiteral board);
void printBoard(BoardEncoded board);
std::vector<BoardEncoded> get_moves(BoardLiteral board);
BoardEncoded getWinningBoard();
bool isWinning(BoardEncoded board_encoded);
std::vector<std::pair<BoardEncoded, uint8_t>> get_available_moves(BoardEncoded board_encoded);
std::deque<uint8_t> read_solution(std::unordered_map<BoardEncoded, uint8_t>& explore_history, uint8_t last_move, BoardEncoded winningBoard);
bool is_valid_solution(const std::deque<uint8_t>& moves, BoardLiteral instance);

bool is_solvable(BoardLiteral instance);
#endif //PIETNASTKA__SOLVER_H_
