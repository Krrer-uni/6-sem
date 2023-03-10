//
// Created by krrer on 09.03.23.
//

#include "graph_algorithms.h"
#include <deque>
std::tuple<verticies, edges> DeepFirstSearch(const Graph &graph) {
  verticies solution;
  edges traverse_tree;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize(), false);
  visited[0] = true;
  while (true) {
    auto not_visited = std::find(visited.cbegin(), visited.cend(), false);
    if (not_visited == visited.cend()) break;
    else {
      size_t index = std::distance(visited.cbegin(), not_visited);
      stack.push_back(index);
      solution.push_back(index);
      visited[index] = true;
    }
    while (!stack.empty()) {
      bool wasnt_added = true;
      for (const auto &outgoing_vert : graph.verticies[stack.back()]) {
        if (visited[outgoing_vert]) continue;
        else {
          traverse_tree.emplace_back(stack.back(), outgoing_vert);
          solution.push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
          wasnt_added = false;
          break;
        }
      }
      if (wasnt_added) stack.pop_back();
    }
  }
  return std::make_tuple(solution, traverse_tree);
}

std::tuple<verticies, edges> BreadthFirstSearch(const Graph &graph) {
  verticies solution;
  edges traverse_tree;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize(), false);
  visited[0] = true;
  while (true) {
    auto not_visited = std::find(visited.cbegin(), visited.cend(), false);
    if (not_visited == visited.cend()) break;
    else {
      size_t index = std::distance(visited.cbegin(), not_visited);
      stack.push_front(index);
      solution.push_back(index);
      visited[index] = true;
    }
    while (!stack.empty()) {
      for (const auto &outgoing_vert : graph.verticies[stack.front()]) {
        if (visited[outgoing_vert]) continue;
        else {
          traverse_tree.emplace_back(stack.front(), outgoing_vert);
          solution.push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
        }
      }
      stack.pop_front();
    }

  }
  return std::make_tuple(solution, traverse_tree);
}

std::tuple<verticies, bool> TopologicalSort(const Graph &graph) {
  verticies solution;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize() + 1, false);
  std::vector<bool> cycle_monitor(graph.getSize() + 1, false);
  visited[0] = true;

  while (true) {
    auto not_visited = std::find(visited.cbegin(), visited.cend(), false);
    if (not_visited == visited.cend()) break;
    else {
      size_t index = std::distance(visited.cbegin(), not_visited);
      stack.push_back(index);
      solution.push_back(index);
      visited[index] = true;
      cycle_monitor[index] = true;
    }
    while (!stack.empty()) {
      bool wasnt_added = true;
      for (const auto &outgoing_vert : graph.verticies.at(stack.back())) {
        if (cycle_monitor.at(outgoing_vert)) {
          return std::make_tuple(solution, true);
        } else if (visited.at(outgoing_vert)) {
          continue;
        } else {
          solution.push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited.at(outgoing_vert) = true;
          cycle_monitor.at(outgoing_vert) = true;
          wasnt_added = false;
          break;
        }
      }
      if (wasnt_added) {
        cycle_monitor.at(stack.back()) = false;
        stack.pop_back();
      }
    }
  }
  return std::make_tuple(solution, false);
}

std::vector<std::vector<size_t>> StronglyConnectedComponent(const Graph &graph) {
  std::vector<std::vector<size_t>> solution;
  std::deque<size_t> dfs_post_order;
  std::deque<size_t> stack;
  std::vector<bool> visited(graph.getSize() + 1, false);
  visited[0] = true;
  while (true) {
    auto not_visited = std::find(visited.cbegin(), visited.cend(), false);
    if (not_visited == visited.cend()) break;
    else {
      size_t index = std::distance(visited.cbegin(), not_visited);
      stack.push_back(index);
      visited[index] = true;
    }
    while (!stack.empty()) {
      bool wasnt_added = true;
      for (const auto &outgoing_vert : graph.verticies[stack.back()]) {
        if (visited[outgoing_vert]) continue;
        else {
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
          wasnt_added = false;
          break;
        }
      }
      if (wasnt_added) {
        dfs_post_order.push_back(stack.back());
        stack.pop_back();
      }

    }
  }
  auto transposed = graph.transpose();
  std::fill(visited.begin(), visited.end(), false);
  assert(stack.empty());
  size_t solution_count = 0;
  while (!dfs_post_order.empty()) {
    size_t index = dfs_post_order.back();
    dfs_post_order.pop_back();
    if (visited.at(index)) {
      continue;
    }
    stack.push_back(index);
    solution.emplace_back();
    solution[solution_count].push_back(index);
    visited[index] = true;
    while (!stack.empty()) {
      bool wasnt_added = true;
      for (const auto &outgoing_vert : transposed[stack.back()]) {
        if (visited[outgoing_vert]) continue;
        else {
          solution[solution_count].push_back(outgoing_vert);
          stack.push_back(outgoing_vert);
          visited[outgoing_vert] = true;
          wasnt_added = false;
          break;
        }
      }
      if (wasnt_added) {
        stack.pop_back();
      }
    }
    solution_count++;
  }
  return solution;
}

std::tuple<verticies, verticies, bool> isBipartite(const Graph &graph) {
  std::array<verticies, 2> partition;
  std::deque<size_t> stack;
  std::vector<int> index_partition(graph.getSize() + 1, -1);
  index_partition[0] = 3;
  while (true) {
    auto not_visited = std::find(index_partition.cbegin(), index_partition.cend(), -1);
    if (not_visited == index_partition.cend()) break;
    else {
      size_t index = std::distance(index_partition.cbegin(), not_visited);
      stack.push_front(index);
      index_partition.at(index) = 0;
      partition.at(index % 2).push_back(index);
    }
    while (!stack.empty()) {
      for (const auto &outgoing_vert : graph.verticies[stack.front()]) {
        int stack_front_partition = index_partition.at(stack.front());

        if (index_partition.at(outgoing_vert) == stack_front_partition)
          return std::make_tuple(partition[0], partition[1], false);
        if (index_partition.at(outgoing_vert) == (stack_front_partition + 1) % 2)
          continue;

        partition.at((stack_front_partition + 1) % 2).push_back(outgoing_vert);
        index_partition.at(outgoing_vert) = (stack_front_partition + 1) % 2;
        stack.push_back(outgoing_vert);
      }
      stack.pop_front();
    }

  }
  return std::make_tuple(partition[0], partition[1], true);
}