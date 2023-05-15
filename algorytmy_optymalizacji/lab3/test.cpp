//
// Created by krrer on 13.05.23.
//
#include "include/algorithms.h"

int main(){
  auto* queue = new algorithms::BinaryHeap(200);
  for(size_t i = 0 ;i < 10 ; i++){
    for(size_t j = 0 ;j < 100 ; j+=10){
      queue->insert({i+j,(int)(10-i)});
    }
  }
  for(size_t i = 1 ;i < 10 ; i++){
    queue->decrease_key({i,(int)(0)});
  }
  for(size_t i = 0 ;i < 10 ; i++){
    queue->delete_elem({i+1,(int)(10)});
  }
  while (!queue->empty()){
    auto x = queue->pop();
    std::cout << x.id << " " << x.weight << "\n";
  }
};