#include <array>
#include "board.h"
#include <cmath>
#pragma once
typedef double (*heuristic_funtion)(Board board, int max_player);

class game_agent
{
private:
    heuristic_funtion heuristic;
    int depth;
    double check_board(Board& board, int depth, int maximize_player, int current_player, double alpha, double beta);
public:
    int find_move(Board board, int player);
    game_agent(int search_depth,heuristic_funtion heuristic);
    ~game_agent();
};

double game_agent::check_board(Board& board, int depth, int maximize_player, int current_player, double alpha, double beta){
    int opponent = (maximize_player == 1) ? 2 : 1;

    if(Gameboard::check_win(board,maximize_player)){
        return INFINITY;
    }
    if( Gameboard::check_win(board,opponent)){
        return -INFINITY;
    }
    if(Gameboard::check_lose(board,maximize_player) ){
        return -INFINITY;
    }
        if(Gameboard::check_win(board,maximize_player)){
        return INFINITY;
    }
    if(depth == 0)
        return heuristic(board, maximize_player);
    int next_player = (current_player == 1) ? 2 : 1;

    if(current_player == maximize_player){
        double curr_max = -INFINITY;
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == 0){
                    board[i][j] = current_player;
                    double board_grade = check_board(
                        board,
                        depth-1,
                        maximize_player,
                        next_player, alpha, beta);
                    board[i][j] = 0;
                    curr_max = std::max(curr_max, board_grade);
                    alpha = std::max(alpha, board_grade);
                    if(alpha >= beta){
                        break;
                    }
                    
                }
            }
        }
        return curr_max;
    }else{
        double curr_min = INFINITY;
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == 0){
                    board[i][j] = current_player;
                    double board_grade = check_board(
                        board,
                        depth-1,
                        maximize_player,
                        next_player, alpha, beta);
                    board[i][j] = 0;
                    curr_min = std::min(curr_min, board_grade);
                    beta = std::min(beta, board_grade);
                    if(alpha >= beta){
                        break;
                    }
                    
                }
            }
        }
        return curr_min;
    }

    return NAN;
}
    
    
    

game_agent::game_agent(int search_depth,heuristic_funtion heuristic){
    depth = search_depth;
    this->heuristic = heuristic;
}

int game_agent::find_move(Board board, int player){
   if(depth == 0){
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == 0)
                    return (i+1)*10+(j+1);
            }
        }   
    }

    int next_player = (player == 1) ? 2 : 1;
    double curr_max = -INFINITY;
    int curr_move = 0;
    for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
            if(board[i][j] == 0){
                board[i][j] = player;
                double board_grade = check_board(
                    board,
                    depth-1,
                    player,
                    next_player,-INFINITY,INFINITY);
                if(board_grade > curr_max){
                    curr_max = board_grade;
                    curr_move = (i+1)*10+(j+1);
                }
                board[i][j] = 0;
            }
        }
    }
    if(curr_max == -INFINITY){
    std::cout << "draw situation\n";
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == 0){
                    board[i][j] = player;
                    if(!Gameboard::check_lose(board,player)){
                    return (i+1)*10+(j+1);
                    }
                    board[i][j] = 0;
                }
            }
        }   
        for(int i =0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                if(board[i][j] == 0){{
                    return (i+1)*10+(j+1);
                    }
                }
            }
        }   
    }
    printf("move with value: %lf\n", curr_max);
    return curr_move;
}


game_agent::~game_agent(){
}

double test_heuristic(Board board, int player){
    double grade = 0;
    for(int i = 0; i < 5; i++){
        for(int j = 0; j < 5; j++){
            if(board[i][j]== player){
                grade += (i * 10 + j);
            }
        }
    }
    return grade;
}

double def_heuristic(Board board, int player){

    if(Gameboard::check_lose(board,player)){
        return -INFINITY+1.0f;
    }
    if(Gameboard::check_win(board,player)){
        return INFINITY-1.0f;
    }

    int opponent = (player == 1) ? 2 : 1;
    
    double grade = 0;

    for (int i = 0; i < 28; i++){
        double add = 1;
        if (board[Gameboard::win[i][0][0]][Gameboard::win[i][0][1]] == player) 
            add *=2;
        if(board[Gameboard::win[i][1][0]][Gameboard::win[i][1][1]] == player)
            add *=2;
        if(board[Gameboard::win[i][2][0]][Gameboard::win[i][2][1]] == player) 
            add *=2;
        if(board[Gameboard::win[i][3][0]][Gameboard::win[i][3][1]] == player)
            add *=2;
        
        grade += add -1;
    }
    return grade;
}