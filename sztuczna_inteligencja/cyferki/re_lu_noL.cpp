#include <iostream>
#include <cmath>
#include <vector>
#include <random>

using namespace std;

// Activation function (ReLU)
double relu(double x) {
    return max(0.0, x);
}

// Derivative of the activation function (ReLU)
double reluDerivative(double x) {
    return (x > 0.0) ? 1.0 : 0.0;
}

// Neural network class
class NeuralNetwork {
private:
    vector<vector<double>> weights1;  // Weight matrix for hidden layer
    vector<vector<double>> weights2;  // Weight matrix for output layer
    vector<double> biases1;           // Bias vector for hidden layer
    vector<double> biases2;           // Bias vector for output layer
    double learningRate;              // Learning rate

public:
    NeuralNetwork(double rate) {
        random_device rd;
        mt19937 gen(rd());
        normal_distribution<double> dist(0.0, 0.1);
        normal_distribution<double> bias_dist(-0.3, 0.3);

        // Initialize weights and biases with random values
        weights1 = {{dist(gen), dist(gen)}, {dist(gen), dist(gen)}, {dist(gen), dist(gen)}, {dist(gen), dist(gen)}};
        weights2 = {{dist(gen), dist(gen), dist(gen), dist(gen)}};
        biases1 = {bias_dist(gen), bias_dist(gen), bias_dist(gen), bias_dist(gen)};
        biases2 = {bias_dist(gen)};
        learningRate = rate;
    }

    vector<double> forward(vector<double>& input) {
        vector<double> hidden(4);
        vector<double> output(1);

        // Calculate values of hidden layer nodes
        for (int i = 0; i < 4; i++) {
            double sum = 0.0;
            for (int j = 0; j < 2; j++) {
                sum += weights1[i][j] * input[j];
            }
            hidden[i] = relu(sum + biases1[i]);
        }

        // Calculate value of output layer node
        double sum = 0.0;
        for (int i = 0; i < 4; i++) {
            sum += weights2[0][i] * hidden[i];
        }
        output[0] = relu(sum + biases2[0]);

        return output;
    }

    void train(vector<vector<double>>& inputs, vector<vector<double>>& targets, int epochs) {
        int numInputs = inputs.size();

        for (int epoch = 0; epoch < epochs; epoch++) {
            // Iterate over training examples
            for (int i = 0; i < numInputs; i++) {
                vector<double> input = inputs[i];
                vector<double> target = targets[i];

                // Forward pass
                vector<double> hidden(4);
                vector<double> output(1);

                for (int j = 0; j < 4; j++) {
                    double sum = 0.0;
                    for (int k = 0; k < 2; k++) {
                        sum += weights1[j][k] * input[k];
                    }
                    hidden[j] = relu(sum + biases1[j]);
                }

                double sum = 0.0;
                for (int j = 0; j < 4; j++) {
                    sum += weights2[0][j] * hidden[j];
                }
                output[0] = relu(sum + biases2[0]);

                // Backpropagation
                vector<double> outputError(1);
                outputError[0] = (target[0] - output[0]) * reluDerivative(output[0]);

                vector<double> hiddenError(4);
                for (int j = 0; j < 4; j++) {
                    double error = 0.0;
                    for (int k = 0; k < 1; k++) {
                        error += weights2[k][j] * outputError[k];
                    }
                    hiddenError[j] = error * reluDerivative(hidden[j]);
                }

                // Update weights and biases
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 2; k++) {
                        weights1[j][k] += learningRate * hiddenError[j] * input[k];
                    }
                }

                for (int j = 0; j < 1; j++) {
                    for (int k = 0; k < 4; k++) {
                        weights2[j][k] += learningRate * outputError[j] * hidden[k];
                    }
                }

                for (int j = 0; j < 4; j++) {
                    biases1[j] += learningRate * hiddenError[j];
                }

                biases2[0] += learningRate * outputError[0];
            }
        }
    }
};

int main() {
    NeuralNetwork neuralNetwork(0.1);

    // Training data for XOR problem
    vector<vector<double>> trainingInputs = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};
    vector<vector<double>> trainingTargets = {{0}, {1}, {1}, {0}};

    // Train the network
    neuralNetwork.train(trainingInputs, trainingTargets, 10000);
    
    // Test the trained network
    for (int i = 0; i < trainingInputs.size(); i++) {
        vector<double> input = trainingInputs[i];
        vector<double> output = neuralNetwork.forward(input);

        // Print input and output
        cout << "Input: " << input[0] << " " << input[1] << " ";
        cout << "Output: " << output[0] << endl;
    }

    return 0;
}
