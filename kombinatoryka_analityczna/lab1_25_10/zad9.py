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

def countSubCycle(array):
    count = 1
    last = 0
    l = len(array)
    for i in range(0, l+1):
        k = last
        last = array[last]
        array[k] = -1
        if last == -1:
            for k in range(1,l-1):
                if array[k] != -1:
                    last = array[k]
                    array[k] = -1
                    count = count + 1
                    break
    return count
            
                


n = 10
array = list(range(0,n))
shuffled = shuffle(array)
print(shuffled)
print(countStablePoint(shuffled))
print(countSubCycle(shuffled))

no_stable = []
stable_point = []
cycles = []
x_axis_data = range(1000,10000,500)
repeats = 10000
for i in x_axis_data:
    print(i)
    no_stable_count = 0
    stable_count = 0
    mean_cycles = []

    for j in range(1,repeats):
        array = list(range(0,n))
        shuffled = shuffle(array)
        p = countStablePoint(shuffled)
        mean_cycles.append(countSubCycle(shuffled))
        if p == 0:
            no_stable_count = no_stable_count + 1
        if p == 1:
            stable_count = stable_count + 1
    no_stable.append(no_stable_count/repeats)
    stable_point.append(stable_count/repeats)
    cycles.append(np.mean(mean_cycles))

plt.plot(x_axis_data,no_stable)
plt.xlabel("Array size")
plt.ylabel("[%] of zero stable point permutations")
plt.ylim(0.0,1.0)
plt.savefig("no_stable_points")
plt.clf()


plt.plot(x_axis_data,stable_point)
plt.xlabel("Array size")
plt.ylabel("[%] of one stable point permutations")
plt.ylim(0.0,1.0)
plt.savefig("one_stable_points")
plt.clf()

plt.plot(x_axis_data,cycles)
plt.xlabel("Array size")
plt.ylabel("number of cycles in permutation")
plt.ylim(0.0)
plt.savefig("mean_cycles")
