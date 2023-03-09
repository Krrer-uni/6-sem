cmake_minimum_required(VERSION 3.24)
project(lab1)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED On)
set(CMAKE_CXX_EXTENSIONS Off)

find_package(fmt)

add_executable(lab1 main.cpp graph.cc graph.h graph_algorithms.cc graph_algorithms.h)
target_link_libraries(lab1 fmt::fmt)