//
// Created by krrer on 29.03.23.
//

#ifndef PIETNASTKA__HEURISTICS_H_
#define PIETNASTKA__HEURISTICS_H_

#include <cstdint>
#include "solver.h"

uint8_t naive(BoardEncoded board);
uint8_t manhattan(BoardEncoded board);
uint8_t manhattan_lc(BoardEncoded board);

#endif //PIETNASTKA__HEURISTICS_H_
