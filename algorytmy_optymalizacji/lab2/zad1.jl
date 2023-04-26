using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK
lotniska = CSV.read("data/zad1/zad1_lotniska.data",DataFrame)
req = CSV.read("data/zad1/zad1_req.data",DataFrame)
limits = CSV.read("data/zad1/zad1_sup_limit.data",DataFrame)

model = Model(GLPK.Optimizer)
set_silent(model)

@variable(model, x[lotniska.firma,req.lotnisko] >= 0)
solution = Matrix(x)
@constraint(
    model,
    [row in eachrow(limits)],
    row.limit >= sum(x[row.firma,:]),
)
@constraint(
    model,
    [row in eachrow(req)],
    row.zapotrzebowanie == sum(x[:,row.lotnisko])
)

function objective(x,y)
    value = 0
    for row in eachrow(lotniska)
        value = x[row.firma,:] .* row[2:end]
    end
    return value
end

suppliers = lotniska.firma
recievers = req.lotnisko


@objective(model, Min, sum( x[row.firma,rec] .* row[rec] for row in eachrow(lotniska), rec in recievers))

optimize!(model)

for sup in suppliers, rec in recievers
    println(sup ,",",rec," = ",value(x[sup,rec]) )
end
println(sum( value(x[row.firma,rec]) .* row[rec] for row in eachrow(lotniska), rec in recievers))
println(model)
