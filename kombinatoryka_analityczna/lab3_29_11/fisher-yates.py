import random
import numpy as np
import matplotlib.pyplot as plt


def shuffle(array):
    l = len(array)
    for i in range(l-1,1,-1):
        a = random.randint(0,i)
        array[a],array[i] = array[i],array[a]
    return array

def countStablePoint(array):
    l = len(array)
    count = 0
    for i in range(0,l-1):
        if array[i] == i:
            count = count + 1
    return count

def fix(n,k):
    sum = 0
    for l in range(0,k):
        A = [x for x in range(0,n)]
        shuffle(A)
        sum += countStablePoint(A)
    return sum/k

k = 10000
x_axis = [x for x in range(1,100)]
val = [fix(x,k) for x in x_axis]


plt.plot(x_axis,val)
plt.xlabel("Array size")
plt.ylabel("mean of stable points")
plt.ylim(0.0)
plt.axhline(1,color='r',linestyle="--")
plt.savefig("mean_stable")