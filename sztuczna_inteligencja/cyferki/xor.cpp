#include <iostream>
#include <array>
#include <random>
#include <tuple>
// #define twodim(x,y) 

typedef std::tuple<double,double> Output; 
class NNetwork
{
private:

    std::array<std::array<double,4>,3> first_layer_weights; // node, input + bias
    std::array<std::array<double,2>,5> second_layer_weights; // node, input + bias
    std::array<double,4> hidden_layer;

public:

    double (*loss_func)(Output, Output) = [](Output a, Output b){
        auto [x,y] = a;
        auto [j,k] = b;
        return ((x-j)*(x-j) + (y-k)*(y-k))/2; 
    };

    double (*trig_func)(double) = [](double a){return std::max(a,0.0);}; //ReLU
    Output propagate(double x, double y);
    Output learn(double x, double y, double label);
    void back_propagate(Output);
    NNetwork(/* args */);
    ~NNetwork();
};

Output NNetwork::propagate(double x, double y)
{
    double output[2];
    for(int i = 0; i < 4; i++){
        hidden_layer[i] = trig_func(x * first_layer_weights[0][i] + y * first_layer_weights[1][i] + first_layer_weights[2][i]);
    }
    for(int i = 0; i < 2; i++){
        output[i] = trig_func(   hidden_layer[0] * second_layer_weights[i][0] + 
                            hidden_layer[1] * second_layer_weights[i][1] +
                            hidden_layer[2] * second_layer_weights[i][2] +
                            hidden_layer[3] * second_layer_weights[i][3] +
                            second_layer_weights[i][4]);
    }
    return {output[0], output[1]};
}

Output NNetwork::learn(double x, double y, double label)
{
    auto out = propagate(x,y);
    auto loss = loss_func(out,{1.0-label,label});

    return {1.0 -loss, loss};
}

NNetwork::NNetwork(/* args */)
{
std::uniform_real_distribution<double> unif(0.0,1.0);
   std::default_random_engine re;
   for(auto perceptor : first_layer_weights){
    for(auto& weight : perceptor){
        weight = unif(re);
    }
   }

   for(auto perceptor : second_layer_weights){
    for(auto& weight : perceptor){
        weight = unif(re);
    }
   }
//    double a_random_double = unif(re);
}

NNetwork::~NNetwork()
{
}


int main(){
    std::cout << " elo\n";
    return 0;
}