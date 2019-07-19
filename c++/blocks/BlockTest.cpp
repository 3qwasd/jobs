#include <iostream>
#include <stdlib.h>
#include "Block.h"
using namespace std;

void showBlock(Block *&block);

int main(){
	Block * block = NULL;
	for(size_t i = 1 ; i != 7 ; ++i){
		for(size_t j = 1 ; j != 4 ; ++j){
			block = new Block(2,2,i*10+j);
			showBlock(block);
			block->rotateACw();
			showBlock(block);
			block->rotateACw();
			showBlock(block);
			block->rotateACw();
			showBlock(block);
		}
	}
	system("PAUSE");
	return 0;
}

void showBlock(Block *&block){

	cout << "x=" << block->x << ",";
	cout << "y=" << block->y << endl;

	int ** shape = block->getCurrShape();
	for(size_t i = 0 ; i != block->height ; ++i){
		for(size_t j = 0 ; j != block->width ; ++j){
			cout << getSignById(shape[i][j]);
		}
		cout << endl;
	}
	cout << endl;
}