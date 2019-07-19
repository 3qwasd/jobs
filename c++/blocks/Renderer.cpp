#include "Renderer.h"
#include <iostream>

void Renderer::render(int **res,const size_t &width,const size_t &height){
	using namespace std;
	for(size_t i = 0 ; i != height ; ++i){
		for(size_t j = 0 ; j != width ; ++j){
			cout << getSignById(res[i][j]);
		}
		cout << endl;
	}
}
