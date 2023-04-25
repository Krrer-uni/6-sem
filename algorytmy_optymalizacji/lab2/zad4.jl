using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK

n = 0
m = 0
k = 0
container = Vector{Tuple{Int,Int}}()

open("data/zad4/data.data") do io
    global n = parse(Int,readline(io))
    global m = parse(Int,readline(io))
    global k = parse(Int,readline(io))
    for line in readlines(io)
        tokens = [parse(Int,k) for k in split(line," ")]
        push!(container,(tokens[1], tokens[2]))
    end
end

model = Model(GLPK.Optimizer)

@variable(model,x[1:n,1:m] >= 0, Bin)
my_solution = Matrix(x)
for (con_x,con_y) in container
    max_x = min(con_x+k,n)  
    min_x = max(con_x-k,1)
    max_y = min(con_y+k,m)
    min_y = max(con_y-k,1)
    @constraint(model, x[con_x,con_y] == 0)
    @constraint(model, (sum(x[min_x:max_x,con_y]) + sum(x[con_x,min_y:max_y])) >= 1)
end

@objective(model,Min,sum(sum(row) for row in eachrow(x)))

optimize!(model)

println(model)

for i in 1:n
    for k in 1:m
        if value(my_solution[i,k]) > 0
            println("x[",i,",",k,"] = ", value(my_solution[i,k]))
        end
    end
end
println(model)
