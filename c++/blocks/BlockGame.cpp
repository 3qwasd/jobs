#include "BlockGame.h"
#include <windows.h>
#include <conio.h>
#include <time.h>

void BlockGame::init(){

	mainLayer = new Layer(MAIN_WIDTH,MAIN_HEIGHT,SIGN_BACKGROUND);

	gameui = new BlockGameUi(0,0,mainLayer);

	blockLayer = new BlockAreaLayer(BLOCK_AREA_WIDTH,BLOCK_AREA_HEIGHT,SIGN_BLANK);

	GameWidget *blockArea = new GameWidget(2,2,blockLayer,1);



	nextBlockLayer = new Layer(NEXT_BLOCK_WIDTH,NEXT_BLOCK_HEIGHT,SIGN_BLANK);

	GameWidget *nextBlockArea = new GameWidget(23,2,nextBlockLayer,1);

	scoreLayer = new Layer(SCORE_PANEL_WIDTH,SCORE_PANEL_HEIGHT,SIGN_BLANK);

	GameWidget *scorePanel = new GameWidget(23,15,scoreLayer,1);

	gameui->addWidget(blockArea);

	gameui->addWidget(nextBlockArea);

	gameui->addWidget(scorePanel);
}

void BlockGame::mainLoop(){

	this->currBlock = NULL;

	size_t start_x = BLOCK_AREA_WIDTH / 2 - 2;

	size_t start_y = 0;

	bool isSurvive;
	char key;
	size_t speed = 2;
	clock_t start;
	clock_t delay = 1000 / speed;
	while(true){

		if(NULL == this->currBlock){
			this->currBlock = new Block(start_x,start_y,11);
			if(this->currBlock->width < 3)
				this->currBlock->x = start_x + 1;
			isSurvive = true;
		}
		start = clock();
		while(clock() - start < delay){
			if(kbhit()){
				key = getch();
				switch(key){
					case KEY_LEFT:
					this->currBlockMove(MOVE_DIERCTION_LEFT,1);
					break;
					case KEY_RIGHT:
					this->currBlockMove(MOVE_DIERCTION_RIGHT,1);
					break;
					case KEY_DOWN:
					isSurvive = this->currBlockMove(MOVE_DIERCTION_DOWN,1);
					break;
					case KEY_CW:
					this->currBlockRotate(ROTATE_DIRECTION_CW);
					break;
					case KEY_ACW:
					this->currBlockRotate(ROTATE_DIRECTION_ACW);					
					break;
					default:
					break;
				}
			}
			if(!isSurvive)
				break;
		}
		if(isSurvive)
			isSurvive = this->currBlockMove(MOVE_DIERCTION_DOWN,1);
		
		if(!isSurvive){
			this->clearLine(this->currBlock->y,this->currBlock->height);
			delete this->currBlock;
			this->currBlock = NULL;
		}
	}
}
void BlockGame::clearLine(const size_t start_y,const size_t _height){
	int cleanedLineCount = 0;
	size_t cleanedLines[4] = {-1, -1, -1, -1}; 
	for(size_t i = start_y; i != start_y + _height; ++i){
		if(this->blockLayer->lineHasFilled(i)){
			this->blockLayer->cleanLine(i);
			cleanedLines[cleanedLineCount++] = i;
		}
	}
	gameui->paint();
	size_t offset = 1;
	for(int i = cleanedLineCount - 1; i != -1; --i){
		size_t startLine = cleanedLines[i] - 1; 
		size_t endLine = i == 0 ? 0 : cleanedLines[i-1] + 1;
		for(size_t y = startLine; y != endLine; --y){
			if(!this->blockLayer->moveLineDown(y,offset)) break;
		}
		offset++;
	}
	gameui->paint();
}
bool BlockGame::currBlockRotate(const short &direction){
	this->blockLayer->cleanArea(this->currBlock->x,this->currBlock->y,this->currBlock->width,this->currBlock->height,this->currBlock->getCurrShape());
	Rect *block_rect = NULL;
	Rect *layer_rect = NULL;
	bool isRollBack = false;
	switch(direction){
		case ROTATE_DIRECTION_CW:
		this->currBlock->rotateCw();
		break;
		case ROTATE_DIRECTION_ACW:
		this->currBlock->rotateACw();
		break;
		default:
		break;
	}
	block_rect = this->currBlock->getRect();
	layer_rect = this->blockLayer->getRect(this->currBlock->x,this->currBlock->y,this->currBlock->width,this->currBlock->height);
	isRollBack |= collisionDetection(block_rect,layer_rect,MOVE_DIERCTION_LEFT);
	if(isRollBack){
		switch(direction){
			case ROTATE_DIRECTION_CW:
			this->currBlock->rotateACw();
			break;
			case ROTATE_DIRECTION_ACW:
			this->currBlock->rotateCw();
			break;
			default:
			break;
		}
	}
	this->blockLayer->addArea(this->currBlock->x,this->currBlock->y,this->currBlock->width,this->currBlock->height,this->currBlock->getCurrShape());
	gameui->paint();
	return true;
}
bool BlockGame::currBlockMove(const short &direction,const size_t &speed){
	this->blockLayer->cleanArea(this->currBlock->x,this->currBlock->y,this->currBlock->width,this->currBlock->height,this->currBlock->getCurrShape());
	size_t x,y,w,h;
	Rect *block_rect = NULL;
	Rect *layer_rect = NULL;
	bool moveSuccess = true;
	for(size_t i = 0; i != speed; ++i){
		switch(direction){
			case MOVE_DIERCTION_RIGHT:
			x = this->currBlock->x + 1;
			y = this->currBlock->y;
			break;
			case MOVE_DIERCTION_LEFT:
			x = this->currBlock->x - 1;
			y = this->currBlock->y;
			break;
			case MOVE_DIERCTION_UP:
			x = this->currBlock->x;
			y = this->currBlock->y - 1;
			break;
			case MOVE_DIERCTION_DOWN:
			x = this->currBlock->x;
			y = this->currBlock->y + 1;
			
			break;
		}
		w = this->currBlock->width;
		h = this->currBlock->height;
		block_rect = this->currBlock->getRect();
		layer_rect = this->blockLayer->getRect(x,y,w,h);
		if(collisionDetection(block_rect,layer_rect,direction)){
			moveSuccess = false;
			delete layer_rect;
			layer_rect = NULL;
			break;
		}
		delete layer_rect;
		layer_rect = NULL;
		this->currBlock->move(direction,1);
	}

	this->blockLayer->addArea(this->currBlock->x,this->currBlock->y,this->currBlock->width,this->currBlock->height,this->currBlock->getCurrShape());
	gameui->paint();
	return moveSuccess;
}

void BlockGame::start(){
	this->mainLoop();
}
