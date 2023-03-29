//
// Created by krrer on 13.03.23.
//

#ifndef PIETNASTKA__SOLVER_H_
#define PIETNASTKA__SOLVER_H_

#include <vector>
#include <array>

#define BOARD_SIZE 16

typedef std::array<char8_t, BOARD_SIZE> BoardLiteral;
typedef uint64_t BoardEncoded;

std::vector<u_int8_t> solve(BoardLiteral instance);
BoardLiteral decode(BoardEncoded encoded);
BoardEncoded encode(BoardLiteral literal);
BoardLiteral getBoard();
void printBoard(BoardLiteral board);
void printBoard(BoardEncoded board);
std::vector<BoardEncoded> get_moves(BoardLiteral board);
BoardEncoded getWinningBoard();
bool isWinning(BoardEncoded board_encoded);
std::vector<std::pair<BoardEncoded, uint8_t>> get_available_moves(BoardEncoded board_encoded);
#endif //PIETNASTKA__SOLVER_H_
