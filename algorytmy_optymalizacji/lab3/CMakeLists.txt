cmake_minimum_required(VERSION 3.24)
project(lab3)

set(CMAKE_CXX_STANDARD 20)

add_executable(dijktra dijkstra.cpp src/fileread.cpp include/fileread.h include/graph_utill.h src/graph_utill.cc
        src/graph_utill.cc src/algorithms.cpp include/algorithms.h)
add_executable(test  test.cpp src/algorithms.cpp include/algorithms.h)