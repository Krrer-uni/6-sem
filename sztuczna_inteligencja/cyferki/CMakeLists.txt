cmake_minimum_required(3.16)
project(wsi_xor)
add_executable(Xor xor.cpp)
add_executable(Xor2 xor_gpt.cpp)

set_property(TARGET Xor PROPERTY CXX_STANDARD 17)
