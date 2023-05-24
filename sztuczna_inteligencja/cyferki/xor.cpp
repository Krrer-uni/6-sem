#include <iostream>
#include <array>
#include <random>
// #define twodim(x,y) 
class NNetwork
{
private:

    std::array<std::array<double,4>,2> first_layer_weights; // input, layer
    std::array<std::array<double,2>,4> second_layer_weights;
    std::array<double,4> hidden_layer;
public:


    int calculate(double x, double y);
    
    NNetwork(/* args */);
    ~NNetwork();
};

int NNetwork::calculate(double x, double y)
{
    for(int i = 0; i < 4; i++){
        hidden_layer[i] = x * first_layer_weights[0][i] + y * first_layer_weights[1][i];
    }
    
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