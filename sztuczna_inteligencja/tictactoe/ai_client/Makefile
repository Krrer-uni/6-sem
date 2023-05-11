SOURCES = $(wildcard *.cpp)
BINARY = $(patsubst %.cpp, %, $(SOURCES))
CC = g++

FLAGS = -W -pedantic -O3 -std=c++17
.PHONY = all clean

all: $(BINARY)

$(BINARY): %: %.cpp board.h
	${CC} $(FLAGS) $^ -o $@
	strip $@

clean:
	rm -f $(BINARY)

