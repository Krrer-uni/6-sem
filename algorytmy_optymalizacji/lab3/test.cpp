//
// Created by krrer on 13.05.23.
//
#include "include/algorithms.h"

int main(){
  algorithms::DialPriorityQueue* queue = new algorithms::DialPriorityQueue(10000,20);
  for(size_t i = 0 ;i < 10 ; i++){
    for(size_t j = 0 ;j < 100 ; j+=10){
      queue->insert({i+j,(int)(10-i)});
    }
  }
  for(size_t i = 0 ;i < 10 ; i++){
    queue->decrease_key({i,(int)(0)});
  }
  for(size_t i = 0 ;i < 10 ; i++){
    queue->decrease_key({i+1,(int)(10)});
  }
  while (!queue->empty()){
    auto x = queue->pop();
    std::cout << x.id << " " << x.weight << "\n";
  }
};