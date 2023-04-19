cmake_minimum_required(VERSION 3.16)
project(pietnastka)

set(CMAKE_CXX_STANDARD 20)

if(NOT CMAKE_BUILD_TYPE)
    set(CMAKE_BUILD_TYPE Release)
endif()
set(CMAKE_BUILD_TYPE Release)
set(CMAKE_CXX_FLAGS "-Wall -Wextra")
set(CMAKE_CXX_FLAGS_DEBUG "-g")
set(CMAKE_CXX_FLAGS_RELEASE "-O3")


add_executable(pietnastka main.cpp solver.h solver.cpp heuristics.cpp heuristics.h BetterPriorityQueue.cpp BetterPriorityQueue.h)
