cmake_minimum_required(VERSION 3.25)
project(lab4)

set(CMAKE_CXX_STANDARD 20)

add_executable(hypercube hypercube.cpp graph_utill.cc graph_utill.h solver.cpp solver.h)
add_executable(bipartite.cpp bipartite.cpp graph_utill.cc graph_utill.h solver.cpp solver.h)
