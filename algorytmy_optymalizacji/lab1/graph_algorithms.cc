//
// Created by krrer on 09.03.23.
//

#include "graph_algorithms.h"
#include <deque>
std::tuple<verticies, edges> DeepFirstSearch(const  Graph& graph){
  verticies solution;
  edges traverse_tree;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize(),false);
  visited[0] = true;
  while(true){
    auto not_visited = std::find(visited.cbegin(),visited.cend(),false);
    if(not_visited == visited.cend()) break;
    else{
      size_t index =std::distance(visited.cbegin(),not_visited);
      stack.push_back(index);
      solution.push_back(index);
      visited[index] = true;
    }
    while(!stack.empty()){
      bool wasnt_added = true;
      for(const auto& outgoing_vert : graph.verticies[stack.back()]){
        if(visited[outgoing_vert]) continue;
        else{
          traverse_tree.emplace_back(stack.back(),outgoing_vert);
          solution.push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
          wasnt_added = false;
          break;
        }
      }
      if(wasnt_added) stack.pop_back();
    }

  }
  return std::make_tuple(solution,traverse_tree);
}

std::tuple<verticies, edges> BreadthFirstSearch(const  Graph& graph){
  verticies solution;
  edges traverse_tree;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize(),false);
  visited[0] = true;
  while(true){
    auto not_visited = std::find(visited.cbegin(),visited.cend(),false);
    if(not_visited == visited.cend()) break;
    else{
      size_t index =std::distance(visited.cbegin(),not_visited);
      stack.push_front(index);
      solution.push_back(index);
      visited[index] = true;
    }
    while(!stack.empty()){
      bool wasnt_added = true;
      for(const auto& outgoing_vert : graph.verticies[stack.front()]){
        if(visited[outgoing_vert]) continue;
        else{
          traverse_tree.emplace_back(stack.back(),outgoing_vert);
          solution.push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
        }
      }
      stack.pop_front();
    }

  }
  return std::make_tuple(solution,traverse_tree);
}

std::tuple<verticies, bool> TopologicalSort(const Graph& graph){}
std::vector<std::vector<size_t>> StronglyConnectedComponent(const Graph& graph){}
std::tuple<verticies, verticies, bool> isBipartite(const Graph& graph){}