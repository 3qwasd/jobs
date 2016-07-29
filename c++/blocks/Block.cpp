#include "Block.h"

void Block::init(){
	this->shapes = new int**[4];//4幀，依次旋转
	for(size_t i = 0 ; i != 4 ; ++i)
		this->shapes[i] = NULL;
	//先判断方块类型是否存在，如果不存在，默认为第一种方块的第一种形态
	short bigType = this->type / 10;
	this->currShape = this->type % 10;
	if(bigType < 1 || bigType > 6)
		bigType = 1;
	if(this->currShape > 4 || this->currShape < 1){
		this->currShape = 1;
	}
	this->type = bigType*10+this->currShape;
	this->shapes[this->currShape-1] = getBlockResByType(this->type,this->height,this->width);
}
void Block::release(){
	if(this->shapes != NULL)
		for(size_t i = 0 ; i != 4 ; i++){
			if(this->shapes[this->currShape-1] != NULL){
				recycleBlock(this->shapes[this->currShape-1],this->height);
				this->currShape = this->currShape >= 4 ? 1 : 1 + this->currShape;
				size_t temp = this->height;
				this->height = this->width;
				this->width = temp;
			}
		}

	this->rect = this->getRect();
	if(rect != NULL)
		delete this->rect;
	
	delete [] this->shapes;
}
int** Block::getCurrShape(){
	return this->shapes[this->currShape-1];
}
int** Block::rotate(const short &direction){
	//旋转是按照方块中心点旋转
	//计算未旋转前方块中心坐标
	size_t core_x = this->x + this->width/2,core_y = this->y + this->height/2;
	int lastShape = this->currShape;
	switch(direction){
		case ROTATE_DIRECTION_CW:
		this->currShape = lastShape >= 4 ? 1 : 1 + lastShape;
		break;
		case ROTATE_DIRECTION_ACW:
		this->currShape = lastShape <= 1 ? 4 : lastShape - 1;
		break;
	}
	if(NULL == this->shapes[this->currShape-1]){
		this->shapes[this->currShape-1] = direction == ROTATE_DIRECTION_CW ?
		rotate_90D(this->shapes[lastShape-1],this->height,this->width):
		rotate_N90D(this->shapes[lastShape-1],this->height,this->width);
	}else{
		size_t temp = this->height;
		this->height = this->width;
		this->width = temp;
	}
	//旋转后，通过原方块中心坐标，计算方块右上角的坐标
	this->x = core_x - this->width/2;
	this->y = core_y - this->height/2;
	return this->shapes[this->currShape-1];
}
int** Block::rotateCw(){
	return this->rotate(ROTATE_DIRECTION_CW);
}
int** Block::rotateACw(){
	return this->rotate(ROTATE_DIRECTION_ACW);
}
void Block::move(const short &direction,const size_t &speed){
	switch(direction){
		case MOVE_DIERCTION_LEFT:
		x -= speed;
		return;
		case MOVE_DIERCTION_RIGHT:
		x += speed;
		return;
		case MOVE_DIERCTION_DOWN:
		y += speed;
		return;
		case MOVE_DIERCTION_UP:
		y -= speed;
		return;
		default:
		return;
	}
}
Rect* Block::getRect(){
	if(this->rect == NULL)
		this->rect = new Rect;
	this->rect->width = this->width;
	this->rect->height = this->height;
	this->rect->matrix = this->getCurrShape();
	return this->rect;
}
void BlockAreaLayer::init(){
	size_t middle = this->width / 2;
	for(size_t i = 0 ; i != 2 ; ++i){
		for(size_t j = 0 ; j != this->width ; ++j){
			if(j >= middle - 2 && j < middle + 2)
				this->materials[i][j] = this->bgSignId;
			else
				this->materials[i][j] = SIGN_ENTRANCE;
		}
	}
}
