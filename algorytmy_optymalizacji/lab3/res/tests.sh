#!/bin/bash

for name in  USA-road-d.CTR
do
 ./binary_heap -d ${name}.gr -ss ${name}.ss -oss results/${name}.ss.res
# ./radix -d ${name}.gr -ss ${name}.ss -oss results/${name}.ss.res
 ./dial -d ${name}.gr -ss ${name}.ss -oss results/${name}.ss.res
done


