#include <array>
#include <iostream>

#pragma once

typedef std::array<std::array<int, 5>, 5> Board;

class Gameboard
{
private:
    Board board;

public:
    inline constexpr static const int win[28][4][2] = {
        {{0, 0}, {0, 1}, {0, 2}, {0, 3}},
        {{1, 0}, {1, 1}, {1, 2}, {1, 3}},
        {{2, 0}, {2, 1}, {2, 2}, {2, 3}},
        {{3, 0}, {3, 1}, {3, 2}, {3, 3}},
        {{4, 0}, {4, 1}, {4, 2}, {4, 3}},
        {{0, 1}, {0, 2}, {0, 3}, {0, 4}},
        {{1, 1}, {1, 2}, {1, 3}, {1, 4}},
        {{2, 1}, {2, 2}, {2, 3}, {2, 4}},
        {{3, 1}, {3, 2}, {3, 3}, {3, 4}},
        {{4, 1}, {4, 2}, {4, 3}, {4, 4}},
        {{0, 0}, {1, 0}, {2, 0}, {3, 0}},
        {{0, 1}, {1, 1}, {2, 1}, {3, 1}},
        {{0, 2}, {1, 2}, {2, 2}, {3, 2}},
        {{0, 3}, {1, 3}, {2, 3}, {3, 3}},
        {{0, 4}, {1, 4}, {2, 4}, {3, 4}},
        {{1, 0}, {2, 0}, {3, 0}, {4, 0}},
        {{1, 1}, {2, 1}, {3, 1}, {4, 1}},
        {{1, 2}, {2, 2}, {3, 2}, {4, 2}},
        {{1, 3}, {2, 3}, {3, 3}, {4, 3}},
        {{1, 4}, {2, 4}, {3, 4}, {4, 4}},
        {{0, 1}, {1, 2}, {2, 3}, {3, 4}},
        {{0, 0}, {1, 1}, {2, 2}, {3, 3}},
        {{1, 1}, {2, 2}, {3, 3}, {4, 4}},
        {{1, 0}, {2, 1}, {3, 2}, {4, 3}},
        {{0, 3}, {1, 2}, {2, 1}, {3, 0}},
        {{0, 4}, {1, 3}, {2, 2}, {3, 1}},
        {{1, 3}, {2, 2}, {3, 1}, {4, 0}},
        {{1, 4}, {2, 3}, {3, 2}, {4, 1}}};
    inline constexpr static const int lose[48][3][2] = {
        {{0, 0}, {0, 1}, {0, 2}}, {{0, 1}, {0, 2}, {0, 3}}, {{0, 2}, {0, 3}, {0, 4}}, {{1, 0}, {1, 1}, {1, 2}}, {{1, 1}, {1, 2}, {1, 3}}, {{1, 2}, {1, 3}, {1, 4}}, {{2, 0}, {2, 1}, {2, 2}}, {{2, 1}, {2, 2}, {2, 3}}, {{2, 2}, {2, 3}, {2, 4}}, {{3, 0}, {3, 1}, {3, 2}}, {{3, 1}, {3, 2}, {3, 3}}, {{3, 2}, {3, 3}, {3, 4}}, {{4, 0}, {4, 1}, {4, 2}}, {{4, 1}, {4, 2}, {4, 3}}, {{4, 2}, {4, 3}, {4, 4}}, {{0, 0}, {1, 0}, {2, 0}}, {{1, 0}, {2, 0}, {3, 0}}, {{2, 0}, {3, 0}, {4, 0}}, {{0, 1}, {1, 1}, {2, 1}}, {{1, 1}, {2, 1}, {3, 1}}, {{2, 1}, {3, 1}, {4, 1}}, {{0, 2}, {1, 2}, {2, 2}}, {{1, 2}, {2, 2}, {3, 2}}, {{2, 2}, {3, 2}, {4, 2}}, {{0, 3}, {1, 3}, {2, 3}}, {{1, 3}, {2, 3}, {3, 3}}, {{2, 3}, {3, 3}, {4, 3}}, {{0, 4}, {1, 4}, {2, 4}}, {{1, 4}, {2, 4}, {3, 4}}, {{2, 4}, {3, 4}, {4, 4}}, {{0, 2}, {1, 3}, {2, 4}}, {{0, 1}, {1, 2}, {2, 3}}, {{1, 2}, {2, 3}, {3, 4}}, {{0, 0}, {1, 1}, {2, 2}}, {{1, 1}, {2, 2}, {3, 3}}, {{2, 2}, {3, 3}, {4, 4}}, {{1, 0}, {2, 1}, {3, 2}}, {{2, 1}, {3, 2}, {4, 3}}, {{2, 0}, {3, 1}, {4, 2}}, {{0, 2}, {1, 1}, {2, 0}}, {{0, 3}, {1, 2}, {2, 1}}, {{1, 2}, {2, 1}, {3, 0}}, {{0, 4}, {1, 3}, {2, 2}}, {{1, 3}, {2, 2}, {3, 1}}, {{2, 2}, {3, 1}, {4, 0}}, {{1, 4}, {2, 3}, {3, 2}}, {{2, 3}, {3, 2}, {4, 1}}, {{2, 4}, {3, 3}, {4, 2}}};

    Gameboard(Board board);
    Gameboard(){};
    void setup();
    void print();
    bool make_move(uint move, uint player);
    bool check_win(int player);
    bool check_lose(int player);
    static bool check_win(const Board& board, int player);
    static bool check_lose(const Board& board, int player);
    Board get_board();
};

Gameboard::Gameboard(Board board){
    this->board = board;
}

bool Gameboard::check_win(int player)
{
    bool w = false;
    for (int i = 0; i < 28; i++)
        if ((board[win[i][0][0]][win[i][0][1]] == player) && (board[win[i][1][0]][win[i][1][1]] == player) && (board[win[i][2][0]][win[i][2][1]] == player) && (board[win[i][3][0]][win[i][3][1]] == player))
            w = true;
    return w;
}

bool Gameboard::check_lose(int player){
      bool l=false;
  for(int i=0; i<48; i++)
    if( (board[lose[i][0][0]][lose[i][0][1]]==player) && (board[lose[i][1][0]][lose[i][1][1]]==player) && (board[lose[i][2][0]][lose[i][2][1]]==player) )
      l=true;
  return l;
}
bool Gameboard::check_win(const Board& board, int player){
    bool w = false;
    for (int i = 0; i < 28; i++)
        if ((board[win[i][0][0]][win[i][0][1]] == player) && (board[win[i][1][0]][win[i][1][1]] == player) && (board[win[i][2][0]][win[i][2][1]] == player) && (board[win[i][3][0]][win[i][3][1]] == player))
            w = true;
    return w;
}
bool Gameboard::check_lose(const Board& board, int player){
    bool l=false;
  for(int i=0; i<48; i++)
    if( (board[lose[i][0][0]][lose[i][0][1]]==player) && (board[lose[i][1][0]][lose[i][1][1]]==player) && (board[lose[i][2][0]][lose[i][2][1]]==player) )
      l=true;
  return l;
}



void Gameboard::setup()
{
    for (auto &gameRow : board)
    {
        for (auto &gameField : gameRow)
        {
            gameField = 0;
        }
    }
}

bool Gameboard::make_move(uint move, uint player)
{
    int row = move/10-1;
    int column = move%10-1;
    if (row > 4 || column > 4 || player > 2)
    {
        std::cout << "Move failed\n";
        return false;
    }
    board[row][column] = player;
    return true;
}

void Gameboard::print()
{
    for (const auto &gameRow : board)
    {
        for (const auto &gameField : gameRow)
        {
             switch(gameField) {
                case 0: printf(" -"); break;
                case 1: printf(" X"); break;
                case 2: printf(" O"); break;
      }
        }
        std::cout << "\n";
    }
}

Board Gameboard::get_board(){
    return board;
}