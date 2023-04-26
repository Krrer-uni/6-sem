using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK

size = 0
start = 0
goal = 0
max_time = 0
cost = Dict{Tuple{Int,Int}, Int}()
time = Dict{Tuple{Int,Int}, Int}()

open("data/zad2/data.data") do io
    global size = parse(Int,readline(io))
    global start = parse(Int,readline(io))
    global goal = parse(Int,readline(io))
    global max_time = parse(Int,readline(io))
    global cost = Dict{Tuple{Int,Int}, Int}()
    global time = Dict{Tuple{Int,Int}, Int}()
    for line in readlines(io)
        tokens = [parse(Int,k) for k in split(line," ")]
        global cost[(tokens[1], tokens[2])] = tokens[3]
        global time[(tokens[1], tokens[2])] = tokens[4]
    end
end

model = Model(GLPK.Optimizer)
# set_silent(model)

# Tworzenie listy sąsiedztwa
adj_list = Dict{Int, Vector{Int}}()
rev_list = Dict{Int, Vector{Int}}()

for i in 1:size
    adj_list[i] = []
    rev_list[i] = []
end
for (u, v) in keys(cost)
    push!(adj_list[u], v)
    push!(rev_list[v], u)

end

# Tworzenie zmiennych decyzyjnych
@variable(model, x[(i,j) in keys(cost)], Bin)

# Ograniczenia modelu
@constraint(model, sum(time[(s,d)] * x[(s,d)] for (s,d) in keys(cost)) <= max_time)

@constraint(model, sum(x[(start,j)] for j in adj_list[start]) - sum(x[(i,start)] for i in rev_list[start]) == 1)

@constraint(model, sum(x[(i,goal)] for i in rev_list[goal]) - sum(x[(goal,j)] for j in adj_list[goal]) == 1)
    for v in keys(adj_list)
         if v != start && v != goal
            @constraint(model, sum(x[(i,v)] for i in rev_list[v]) - sum(x[(v,j)]   for j in adj_list[v]) == 0)
    end
end

# Cel optymalizacji
@objective(model, Min, sum(cost[(s,d)] * x[(s,d)] for (s,d) in keys(cost)))

optimize!(model)

# Wyświetlanie wyniku
println("Znaleziona ścieżka:")
curr = start
while curr != goal
    for v in adj_list[curr]
        if value(x[(curr,v)]) > 0.5
            println(curr, " -> ", v)
            global curr = v
            break
        end
    end
end
println("Koszt ścieżki: ", objective_value(model))
println(model)
