using CSV
using DataFrames
using JuMP
using HiGHS
using GLPK

# Read data from CSV files
machines_info = CSV.read("data/zad5/zad5_machines.data", DataFrame)
products_info = CSV.read("data/zad5/zad5.data", DataFrame)

# Create the optimization model
model = Model(GLPK.Optimizer)
set_silent(model)

# Define decision variables
products = products_info.produkt
machines_list = machines_info.maszyna
@variable(model, x[products] >= 0)

# Define constraints
for m in machines_list
    @constraint(
        model,
        sum(x .* products_info[:, m]) .<= machines_info[machines_info.maszyna.==m, :].limit_czasu*60
    )
end

@constraint(
    model,
    [p in products],
    x[p] .<= products_info[products_info.produkt.==p, :].popyt
)

# Define objective function
fun = sum(
    x[p] * (
        products_info[products_info.produkt.==p, :].cena
        - products_info[products_info.produkt.==p, :].materiał
        - sum(
            products_info[products_info.produkt.==p, m] .* machines_info[machines_info.maszyna.==m, :].koszt/60 for m in machines_list
        )
    ) for p in products
)
@objective(model, Max, sum(fun))

# Solve the optimization problem
optimize!(model)

# Print the optimal solution
println("Optimal solution:")
for p in products
    println("x[$p] = $(value(x[p]))")
end
println("Objective function value: $(objective_value(model))")
