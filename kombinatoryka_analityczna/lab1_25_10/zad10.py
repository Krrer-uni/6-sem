import random
import numpy as np
import matplotlib.pyplot as plt
from itertools import product

def genarray(len):
    array = []
    for i in range(1,len):
        r = random.randint(0,1)
        array.append(r)
    return array

def checkSubArray(array, subarray):
    for i in range(0,len(array) - len(subarray)):
        if array[i:i+len(subarray)] == subarray:
            return True
    return False

aaa = []
abb = []
mean_aaa = []
x_axis_data = range(1,50)
repeats = 10000
for i in x_axis_data:
    print(i)
    aaa_count = 0
    abb_count = 0

    for j in range(1,repeats):
        shuffled = genarray(i)
        if checkSubArray(shuffled, [0,0,0]):
            aaa_count = aaa_count + 1
            
        if checkSubArray(shuffled, [0,1,1]):
            abb_count = abb_count + 1


    aaa.append(aaa_count)
    abb.append(abb_count)
    mean_aaa.append(aaa_count/repeats)

plt.plot(x_axis_data,aaa)
plt.xlabel("Array length")
plt.ylabel("Number of aaa subarrays")
plt.ylim(0.0)
plt.savefig("aaa")
plt.clf()


plt.plot(x_axis_data,abb)
plt.xlabel("Array length")
plt.ylabel("Number of abb subarrays")
plt.ylim(0.0)
plt.savefig("abb")
plt.clf()

plt.plot(x_axis_data,mean_aaa)
plt.xlabel("Array length")
plt.ylabel("Mean number of aaa subarrays")
plt.ylim(0.0)
plt.savefig("mean_aaa")
print(np.mean(aaa))