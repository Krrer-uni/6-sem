//
// Created by krrer on 11.05.23.
//

#ifndef LAB3_SRC_ALGORITHMS_H_
#define LAB3_SRC_ALGORITHMS_H_

#include <cstddef>
#include <vector>
#include <queue>
#include <unordered_map>
#include <cmath>
#include "graph_utill.h"

namespace algorithms {
struct dijstra_return_data {
  size_t verticies;
  size_t edges;
  int lowest_cost;
  int highest_cost;
  union {
    double time;
    int path;
  } info;

  std::string toString() {
    return "verticies: [" + std::to_string(verticies) + "] edges: [" + std::to_string(edges) +
        "] lowest: [" + std::to_string(lowest_cost) + "] highest: [" + std::to_string(highest_cost)
        + "]";
  }
};

class PriorityQueue {
 public:
  class Vertice {
   public:
    size_t id;
    int weight;
    bool operator<(Vertice const &vertice) const {
      return this->weight > vertice.weight;
    }
  };
  size_t size;
  virtual void insert(Vertice vertice) {};
  virtual Vertice pop() {};
  virtual void decrease_key(Vertice vertice) {};
  virtual void emplace(size_t id, int weight) {};
  virtual bool empty() {};
};

class StdPriorityQueue : public PriorityQueue {
 private:
  std::priority_queue<Vertice> priority_queue;
 public:
  StdPriorityQueue() {
    size = 0;
  }
  void insert(Vertice vertice) override {
    priority_queue.push(vertice);
    size++;
  };
  void emplace(size_t id, int weight) override {
    priority_queue.push(PriorityQueue::Vertice{id, weight});
    size++;
  };
  Vertice pop() override {
    size--;
    auto v = priority_queue.top();
    priority_queue.pop();
    return v;
  };
  void decrease_key(Vertice vertice) override {
    insert(vertice);
  };
  bool empty() override {
    return size == 0;
  }
};

class DialPriorityQueue : public PriorityQueue {
 private:
  struct DialVertice {
    size_t vertice;
    int tag;
    std::_List_iterator<size_t> self = {};
    bool finilized = false;
  };
  size_t current_elem;
  size_t C;
  size_t size;
  std::vector<std::list<size_t>> containers;
  std::unordered_map<size_t, DialVertice> verticies_bank;

 public:
  DialPriorityQueue(size_t C) {
    this->size = 0;
    this->current_elem = 0;
    this->C = C + 1;
    containers = std::vector<std::list<size_t>>(C + 1);
  }
  void insert(algorithms::PriorityQueue::Vertice vertice) override {
    if (!verticies_bank.contains(vertice.id)) {
      size++;
      verticies_bank.insert({vertice.id,
                             DialVertice{.vertice = vertice.id, .tag = vertice.weight}});
      auto cont_id = (vertice.weight) % (C + 1);
      containers[cont_id].push_front(vertice.id);
      verticies_bank[vertice.id].self = containers[cont_id].begin();
    } else
      assert(false);
  }
  Vertice pop() override {
    size--;
    while (containers[current_elem % (C + 1)].empty()) {
      current_elem++;
    }
    assert(!containers[current_elem % (C + 1)].empty());
    size_t elem = containers[current_elem % (C + 1)].front();
    containers[current_elem % (C + 1)].pop_front();
    verticies_bank[elem].finilized = true;

    return {elem, verticies_bank[elem].tag};
  }

  void decrease_key(Vertice vert) override {
    auto [id, tag] = vert;
    assert(verticies_bank.contains(id));
    auto &elem = verticies_bank[id];
    if (elem.finilized)
      return;
    if (elem.tag < tag) {
      return;
    }
    containers[elem.tag % (C + 1)].erase(elem.self);
    containers[tag % (C + 1)].push_front(id);
    elem.tag = tag;
    elem.self = containers[tag % (C + 1)].begin();
  }

  bool empty() override {
    return size == 0;
  }
};

class RadixHeap : public PriorityQueue {
 private:
  std::unordered_map<size_t, int> vert_map;
  struct container {
    size_t lower_bound;
    std::list<Vertice> data;
    bool empty() const {
      return data.empty();
    }
    Vertice top()  {
      class vertcompare {
       public:
        bool operator()(Vertice a, Vertice b){
          return a.weight > b.weight; };
      };
      data.sort(vertcompare());
      return data.front();
    }
  };
  std::unordered_map<size_t, int> vert_weights;
  std::vector<container> containers;
  size_t C;
  size_t cont_pos;
  size_t cont_size;
 public:

  explicit RadixHeap(size_t C, size_t n) {
    this->C = C;
    this->cont_pos = 0;
    this->cont_size = (size_t) (std::log2(C * n) + 1);
    this->size = 0;
    containers = std::vector<container>(cont_size);
    containers[0].lower_bound = 0;
    containers[1].lower_bound = 1;
    for (auto x = containers.begin() - 2; x != containers.end(); x++) {
      x->lower_bound = 2 * (x - 1)->lower_bound;
    }
  }

  void insert(Vertice vertice) override {
    assert(!vert_weights.contains(vertice.id));
    size++;
    auto x = std::find_if(containers.begin(), containers.end(), [vertice](const container &c) {
      return vertice.weight < c.lower_bound && !c.empty();
    });
    x--;
    x->data.push_front(vertice);
    vert_weights.insert({vertice.id, vertice.weight});
  }

  Vertice pop() override {
    size--;
    size_t curr_cont = 0;
    while (containers[curr_cont].data.empty()) {
      curr_cont++;
    }
    if (containers[curr_cont].data.size() == 1) {
      auto elem = containers[curr_cont].data.front();
      containers[curr_cont].data.pop_front();
      return elem;
    } else {
      auto elem = containers[curr_cont].top();
      size_t new_lower_bound = elem.weight;
      containers[0].lower_bound = new_lower_bound;
      int p = 0;
      for(int i = 0; i < curr_cont; i++){
        containers[i].lower_bound = new_lower_bound +;
      }
    }
  }

  void decrease_key(Vertice vertice) override {

  }
  void emplace(size_t id, int weight) override {

  }
  bool empty() override {

  }
};

dijstra_return_data runDijsktra(Graph graph, size_t source, size_t goal, PriorityQueue *container);
}
#endif //LAB3_SRC_ALGORITHMS_H_
