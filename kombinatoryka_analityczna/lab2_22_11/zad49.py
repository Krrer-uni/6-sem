import numpy as np
import matplotlib.pyplot as plt
from math import log2

def sigma(n):
    if sigma.array[n] == -1 :
        sum = 0
        for i in range(1,n+1):
            if n % i == 0 :
                sum +=i
        sigma.array[n] = sum
    return sigma.array[n]
     
sigma.array = [-1 for _ in range(100+2)]


def p(n):
    p.count +=1
    if p.pn[n] != -1:
        return p.pn[n]
    if n == 0:
        return 1

    sum = 0
    for i in range(n+1,1,-1):
        sum += sigma(i) * p(n-i)
    p.pn[n] = sum/n
    return int(p.pn[n])
p.count = 0
p.pn = [-1 for _ in range(100+2)]
p.pn[0] = 1

p(10)
print(p.count)
# for i in range(0,101):
#     print(p(i))