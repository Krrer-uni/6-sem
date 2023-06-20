using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK


open("gplk_cube.lp") do f
    line = 0

    x = parse(Int,(readline(f)))
    start = parse(Int,(readline(f))) +1
    goal = parse(Int,(readline(f))) +1
    outgoing_c = parse(Int,(readline(f)))
    outgoing = []

    for i in 1:outgoing_c
        push!(outgoing,parse(Int,(readline(f))))
    end
    adj_list = Dict{Int, Vector{Int}}()
    rev_list = Dict{Int, Vector{Int}}()

    cost = Dict{Tuple{Int,Int}, Int}()

    for i in 1:x
        adj_list[i] = []
        rev_list[i] = []
    end

    while ! eof(f)
        constr = split(readline(f))
        cost[(parse(Int,constr[1])+1,parse(Int,constr[2])+1)] =
        parse(Int,constr[3])

        push!(adj_list[parse(Int,constr[1])+1],
        parse(Int,constr[2])+1)
        push!(rev_list[parse(Int,constr[2])+1],
        parse(Int,constr[1])+1)
    end
    print(adj_list)



    model = Model(GLPK.Optimizer)

    @variable(model, x[(i,j) in keys(cost)], Int)

    @constraint(model, sum(x[(i,goal)] for i in rev_list[goal]) - sum(x[(goal,j)] for j in adj_list[goal]) == sum(x[(start,j)] for j in adj_list[start]) - sum(x[(i,start)] for i in rev_list[start]))

    for v in keys(adj_list)
        if v != start && v != goal
            @constraint(model, sum(x[(i,v)] for i in rev_list[v]) - sum(x[(v,j)]  for j in adj_list[v]) == 0)
        end
    end
    for (a,b) in keys(cost)
        @constraint(model, x[(a,b)] <= cost[(a,b)])
    end

    @objective(model, Max, sum(x[(start,j)] for j in adj_list[start]))

    optimize!(model)
    solution_summary(model)
    println("max flow: ", objective_value(model))

    # println(model)

end
