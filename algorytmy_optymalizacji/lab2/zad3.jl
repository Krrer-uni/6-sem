using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK


minimal = CSV.read("data/zad3/minimal.csv",DataFrame)
maximal = CSV.read("data/zad3/maximal.csv",DataFrame)

minimalhood = CSV.read("data/zad3/minimalhood.csv",DataFrame)
minimalshift = CSV.read("data/zad3/minimalshift.csv",DataFrame)

model = Model(GLPK.Optimizer)

shifts = names(minimal)
hoods = nrow(minimalshift)

@variable(model , x[shifts,1:hoods] >= 0,Int)
solution = Matrix(x)

for shift in 1:length(shifts)-1
    @constraint(model,sum(x[shifts[shift],:]) == sum(x[shifts[shift+1],:]) )
end   

for i in shifts, h in 1:hoods
    @constraint(model, minimal[h,i] <= x[i,h] <= maximal[h,i])
end

@constraint(model,[row in eachrow(minimalhood)], row[1] <= sum(x[:,rownumber(row)]))

# @constraint(model,[row in eachrow(minimalshift)], row[1] >= sum(x[shifts[rownumber(row)],:]))

@objective(model,Min,sum(x[shifts[1],:]))
optimize!(model)
println("Wartości zmiennych decyzyjnych:")
for shift in 1:length(shifts)
    for h in 1:hoods
        println("x[", shifts[shift], ",", h, "] = ", value(solution[shift,h]))
    end
end
println("Wynik optymalizacji: ", objective_value(model))
solution_summary(model)
println(model)