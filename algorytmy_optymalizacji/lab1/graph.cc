//
// Created by krrer on 07.03.23.
//

#include "graph.h"
Graph::Graph(size_t verticies_size, bool isDirected) :
    verticies_size(verticies_size),
    verticies(verticies_size+1),
    isDirected(isDirected) {}

void Graph::addEdge(size_t source, size_t dest) {
  verticies[source].push_back(dest);
  if(isDirected){
    verticies[dest].push_back(source);
  }
}
size_t Graph::getSize() const {
  return verticies_size;
}

std::shared_ptr<Graph> readGraphFromFile(std::string filename){
  size_t number_of_verticies;
  size_t number_of_edges;
  bool isDirected;
  std::string buffer;

  std::ifstream graphFile;
  graphFile.open(filename);
  if(!graphFile.is_open()){
    std::cout << "Couldn't open file " + filename + "\n";
    return nullptr;
  }
  getline(graphFile,buffer);
  if(buffer == "D\r"){
    isDirected = true;
  } else{
    isDirected = false;
  }
  getline(graphFile,buffer);
  number_of_verticies = std::stoi(buffer);
  getline(graphFile,buffer);
  number_of_edges = std::stoi(buffer);

  std::shared_ptr<Graph> graph = std::make_shared<Graph>(number_of_verticies,isDirected);
  for(size_t i = 0; i < number_of_edges; i++){
    getline(graphFile,buffer);
    std::vector<std::string> words;
    boost::split(words,buffer, boost::is_any_of(" "));
    graph->addEdge(std::stoi(words[0]), std::stoi(words[1]));
  }

  return graph;
}