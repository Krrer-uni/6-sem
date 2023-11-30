import matplotlib.pyplot as plt
from fractions import Fraction

def read_numbers_from_file(file_path):
    numbers = []
    with open(file_path, 'r') as file:
        for line in file:
            # Convert the fraction string to a float
            float_value = float(line)
            numbers.append(float_value)
    return numbers

def create_plot(numbers):
    plt.plot(numbers)
    plt.xlabel('n')
    plt.ylabel('r_n')
    plt.title('coefficients of generetaing function 1/(1-x)^x')
    plt.show()

if __name__ == "__main__":
    file_path = "coefficients.txt"  # Replace with the actual path to your file
    numbers = read_numbers_from_file(file_path)
    create_plot(numbers)
