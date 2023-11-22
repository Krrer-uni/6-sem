import numpy as np
import matplotlib.pyplot as plt
from math import log2
def sd(n):
    bn = str(bin(n))[2:]
    return bn.count('1')



def s(n):
    sum = 0
    for k in range(1,n):
        sum += sd(k)
    return sum

a = []
x_axis = range(2,1024)

for i in x_axis:
    a.append(s(i) - (0.5* i * log2(i)))

print(a)

plt.plot(x_axis,a)
plt.xlabel("n")
plt.ylabel("s(n) - a(n)")
plt.savefig("s-a")
plt.clf()