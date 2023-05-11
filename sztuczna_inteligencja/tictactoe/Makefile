SOURCES = $(wildcard *.cpp)
BINARY = $(patsubst %.cpp, %, $(SOURCES))
CC = g++

FLAGS = -W -pedantic -O3
.PHONY = all clean

all: $(BINARY)

$(BINARY): %: %.cpp
	${CC} $(FLAGS) $^ -o $@
	strip $@

clean:
	rm -f $(BINARY)

