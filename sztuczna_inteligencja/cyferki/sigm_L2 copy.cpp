#include <iostream>
#include <cmath>
#include <vector>
#include <random>
#include <numeric>

using namespace std;

// Activation function (ReLU)
double relu(double x) {
    return 1.0 / (1.0 + exp(-x));
}

// Derivative of the activation function (ReLU)
double reluDerivative(double x) {
    double sigmoidX = relu(x);
    return sigmoidX * (1.0 - sigmoidX);
}

// Normalize the input data using L2 normalization
void normalizeData(vector<vector<double>>& data) {
    int numInputs = data[0].size();

    for (int i = 0; i < numInputs; i++) {
        double sumSquared = 0.0;
        for (int j = 0; j < data.size(); j++) {
            sumSquared += data[j][i] * data[j][i];
        }

        double norm = sqrt(sumSquared);
        for (int j = 0; j < data.size(); j++) {
            data[j][i] /= norm;
        }
    }
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

        // Initialize weights and biases with random values
        weights1 = {{dist(gen), dist(gen)}, {dist(gen), dist(gen)}, {dist(gen), dist(gen)}, {dist(gen), dist(gen)}};
        weights2 = {{dist(gen), dist(gen), dist(gen), dist(gen)}};
        biases1 = {dist(gen), dist(gen), dist(gen), dist(gen)};
        biases2 = {dist(gen)};
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

        // Normalize the input data using L2 normalization
        normalizeData(inputs);

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
    neuralNetwork.train(trainingInputs, trainingTargets, 100000);

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
