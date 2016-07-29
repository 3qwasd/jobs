
#ifndef BLOCKGAME_H
#define BLOCKGAME_H
#include "Utils.h"
#include "BlockGameUi.h"
#include "Layer.h"
#include "Block.h"
class BlockGame{
private:
	BlockGameUi *gameui;
	Layer *mainLayer;
	Layer *blockLayer;
	Layer *nextBlockLayer;
	Layer *scoreLayer;
	Block *currBlock;
	Block *nextBlock;
	size_t score;
public:
	BlockGame():score(0){
		this->init();
	}
	void mainLoop();
	void start();
	bool currBlockMove(const short &direction,const size_t &speed);
	bool currBlockRotate(const short &direction);
	void clearLine(const size_t start_y,const size_t _height);
private:
	void init();
	void canMove(Block *&block);
};

#endif