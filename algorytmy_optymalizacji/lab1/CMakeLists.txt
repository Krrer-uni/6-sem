cmake_minimum_required(VERSION 3.22.3)
project(lab1)

set(CMAKE_CXX_STANDARD 20)
set(CMAKE_CXX_STANDARD_REQUIRED On)
set(CMAKE_CXX_EXTENSIONS Off)

set(WORKING_DIRECTORY )
find_package(fmt)

add_executable(zad1 zad1.cpp graph.cc graph.h graph_algorithms.cc graph_algorithms.h)
target_link_libraries(zad1 fmt::fmt)

add_executable(zad2 zad2.cpp graph.cc graph.h graph_algorithms.cc graph_algorithms.h)
target_link_libraries(zad2 fmt::fmt)

add_executable(zad3 zad3.cpp graph.cc graph.h graph_algorithms.cc graph_algorithms.h)
target_link_libraries(zad3 fmt::fmt)

add_executable(zad4 zad4.cpp graph.cc graph.h graph_algorithms.cc graph_algorithms.h)
target_link_libraries(zad4 fmt::fmt)