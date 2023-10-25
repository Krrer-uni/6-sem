import numpy as np

def factorial(n):
    r = 1
    for i in range(1,n+1):
        r = r * i
    return r

def genInv(f,n):
    inv_el = []
    if(n > 0):
        inv_el.append(1/f(0))
    for i in range(1,n):
        sum = 0
        for k in range(1,i+1):
            sum = sum + f(k) * inv_el[i-k]
        inv_el.append(-sum/f(0))
    return inv_el

id = lambda x: 1
exp = lambda x: pow(2,x)
inv_factor = lambda  x: 1/factorial(x)

fun_set = [id, exp, factorial, inv_factor]

for fun in fun_set:
    a = []
    for i in range(10):
        a.append(fun(i))
    print(a)
    print(genInv(fun,10))
